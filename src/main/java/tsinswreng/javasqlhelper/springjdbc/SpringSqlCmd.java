package tsinswreng.javasqlhelper.springjdbc;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSetMetaData;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tsinswreng.javasqlhelper.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpringSqlCmd implements ISqlCmd {
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String sql;
	private Map<String, Object> namedParameters;
	private List<Object> positionalArgs;

	public SpringSqlCmd(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// @Override
	// public Iterable<Map<String, Object>> run() {
	// 	if (namedParameters != null) {
	// 		// 命名参数查询
	// 		return jdbcTemplate.queryForList(sql, namedParameters);
	// 	}
	// 	// else if (positionalArgs != null) {
	// 	// 	// 位置参数查询（转换为命名参数格式）
	// 	// 	SqlParameterSource params = new MapSqlParameterSource()
	// 	// 		.addValue("args", positionalArgs.toArray());
	// 	// 	return jdbcTemplate.queryForList(sql, params);
	// 	// }
	// 	else {
	// 		// 无参数查询
	// 		return jdbcTemplate.queryForList(sql, (Map<String, ?>) null);
	// 	}
	// }

	@Override
	public Iterable<Map<String, Object>> run() {
		return () -> {
			try {
				// 创建懒加载迭代器
				return jdbcTemplate.query(
					sql,
					namedParameters != null ? namedParameters : Collections.emptyMap(),
					new ResultSetExtractor<Iterator<Map<String, Object>>>() {
						@Override
						public Iterator<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
							return new LazyResultIterator(rs);
						}
					}
				);
			} catch (DataAccessException e) {
				throw new RuntimeException("Query execution failed", e);
			}
		};
	}

	// 懒加载迭代器实现
	private static class LazyResultIterator implements Iterator<Map<String, Object>> {
		private final ResultSet rs;
		private final SqlRowSetMetaData metaData;
		private boolean hasNext;

		public LazyResultIterator(ResultSet rs) throws SQLException {
			this.rs = rs;
			this.metaData = new ResultSetWrappingSqlRowSetMetaData(rs.getMetaData());
			this.hasNext = rs.next(); // 预取第一行
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public Map<String, Object> next() {
			if (!hasNext) throw new NoSuchElementException();

			try {
				Map<String, Object> row = new LinkedHashMap<>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				hasNext = rs.next(); // 预取下一行
				return row;
			} catch (SQLException e) {
				throw new RuntimeException("Error reading row", e);
			}
		}
	}

	@Override
	public ISqlCmd rawArgs(Map<String, Object> args) {
		this.positionalArgs = null;
		for(var kv : args.entrySet()){
			var argName = kv.getKey();
			var argVal = kv.getValue();
			namedParameters.put(":"+argName, argVal);
		}
		return this;
	}

	@Override
	public ISqlCmd resolvedArgs(Map<String, Object> args) {
		this.namedParameters = args;
		this.positionalArgs = null;
		return this;
	}

	@Override
	public ISqlCmd args(List<Object> args) {
		throw new RuntimeException("use named parameter instead");
	}

	@Override
	public ISqlCmd withCtx(IDbFnCtx dbFnCtx) {
		// 设置上下文（实际由 Spring 管理）
		//this.dbFnCtx = dbFnCtx;
		return this;
	}

	@Override
	public void close() {
		// AutoCloseable：Spring 自动关闭连接，无需操作
	}
}
