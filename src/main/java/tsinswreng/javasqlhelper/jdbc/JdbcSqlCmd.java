package tsinswreng.javasqlhelper.jdbc;

import tsinswreng.javasqlhelper.IDbFnCtx;
import tsinswreng.javasqlhelper.ISqlCmd;
import java.sql.*;
import java.util.*;
import lombok.Data;


@Data
public class JdbcSqlCmd implements ISqlCmd, AutoCloseable {
	private PreparedStatement rawCmd;
	private String sql;

	public JdbcSqlCmd(PreparedStatement stmt) {
		this.rawCmd = stmt;
	}

	// 设置事务上下文（同步）
	public ISqlCmd withCtx(IDbFnCtx ctx) {
		if (ctx != null && ctx.getTxn() != null) {
			try {
				rawCmd.getConnection().setAutoCommit(false);
				//rawCmd.getConnection().setTransactionIsolation(ctx.getTxn().getIsolationLevel()); //TODO
			} catch (SQLException e) {
				throw new RuntimeException("事务设置失败", e);
			}
		}
		return this;
	}

	public ISqlCmd resolvedArgs(Map<String, Object> args){
		throw new RuntimeException("jdbc不支持按名參數、只支持?佔位符");
	}

	//jdbc不支持按名參數、只支持?佔位符
	public ISqlCmd rawArgs(Map<String, Object> args) {
		throw new RuntimeException("jdbc不支持按名參數、只支持?佔位符");
		// try {
		// 	rawCmd.clearParameters(); // 清空旧参数
		// 	for (Map.Entry<String, Object> entry : params.entrySet()) {
		// 		//rawCmd.setObject("@" + entry.getKey(), codeValToDbVal(entry.getValue()));
		// 	}
		// } catch (SQLException e) {
		// 	throw new RuntimeException("参数绑定失败", e);
		// }
		// return this;
	}

	// 位置参数版
	public ISqlCmd args(List<Object> args) {
		try {
			rawCmd.clearParameters();
			for (int i = 0; i < args.size(); i++) {
				rawCmd.setObject(i + 1, codeValToDbVal(args.get(i))); // JDBC索引从1开始
			}
		} catch (SQLException e) {
			throw new RuntimeException("参数绑定失败", e);
		}
		return this;
	}

	private Object codeValToDbVal(Object codeVal) {
		return codeVal;
	}

	// public Iterable<Map<String, Object>> run() {
	// 	List<Map<String, Object>> resultList = new ArrayList<>();
	// 	try (ResultSet rs = rawCmd.executeQuery()) {
	// 		ResultSetMetaData metaData = rs.getMetaData();
	// 		int columnCount = metaData.getColumnCount();

	// 		while (rs.next()) {
	// 			Map<String, Object> row = new HashMap<>();
	// 			for (int i = 1; i <= columnCount; i++) {
	// 				row.put(metaData.getColumnName(i), rs.getObject(i));
	// 			}
	// 			resultList.add(row);
	// 		}
	// 	} catch (SQLException e) {
	// 		throw new RuntimeException("查询执行失败", e);
	// 	}
	// 	return resultList;
	// }

	public Iterable<Map<String, Object>> run() {
		return () -> new ResultSetIterator();
	}

	private class ResultSetIterator implements Iterator<Map<String, Object>> {
		private final ResultSet rs;
		private final ResultSetMetaData metaData;
		private boolean hasNext;
		private boolean initialized = false;

		public ResultSetIterator() {
			try {
				this.rs = rawCmd.executeQuery();
				this.metaData = rs.getMetaData();
				// 初始化时预取第一行数据状态
				hasNext = rs.next();
			} catch (SQLException e) {
				throw new RuntimeException("ResultSet初始化失败", e);
			}
		}

		@Override
		public boolean hasNext() {
			if (!initialized) {
				initialized = true;
				return hasNext;
			}
			try {
				hasNext = rs.next();
				return hasNext;
			} catch (SQLException e) {
				closeResources();
				throw new RuntimeException("数据遍历失败", e);
			}
		}

		@Override
		public Map<String, Object> next() {
			if (!hasNext) throw new NoSuchElementException();
			try {
				Map<String, Object> row = new HashMap<>();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i)); // 动态构建单行数据
				}
				return row;
			} catch (SQLException e) {
				closeResources();
				throw new RuntimeException("数据解析失败", e);
			}
		}

		private void closeResources() {
			try {
				if (rs != null) rs.close(); // 确保资源关闭
			} catch (SQLException e) {
				// 日志记录异常
			}
		}
	}


	// 资源释放（同步关闭）
	@Override
	public void close() {
		try {
			if (rawCmd != null) rawCmd.close();
		} catch (SQLException e) {
			throw new RuntimeException("资源关闭失败", e);
		}
	}
}
