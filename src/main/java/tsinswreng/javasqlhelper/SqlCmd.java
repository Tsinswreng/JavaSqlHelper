package tsinswreng.javasqlhelper;

import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class SqlCmd implements AutoCloseable {
	private PreparedStatement rawCmd;
	private String sql;

	// 构造函数：接受PreparedStatement
	public SqlCmd(PreparedStatement stmt) {
		this.rawCmd = stmt;
	}

	// 设置事务上下文（同步）
	public SqlCmd withCtx(IDbFnCtx ctx) {
		if (ctx != null && ctx.getTxn() != null) {
			try {
				rawCmd.getConnection().setAutoCommit(false);
				//rawCmd.getConnection().setTransactionIsolation(ctx.getTxn().getIsolationLevel());
			} catch (SQLException e) {
				throw new RuntimeException("事务设置失败", e);
			}
		}
		return this;
	}

	// 命名参数版（@key格式）
//AI曰jdbc不支持按名參數、只支持?佔位符
	public SqlCmd args(Map<String, Object> params) {
		try {
			rawCmd.clearParameters(); // 清空旧参数
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				rawCmd.setObject("@" + entry.getKey(), codeValToDbVal(entry.getValue()));
			}
		} catch (SQLException e) {
			throw new RuntimeException("参数绑定失败", e);
		}
		return this;
	}

	// 位置参数版（@0, @1...）
	public SqlCmd args(List<Object> params) {
		try {
			rawCmd.clearParameters();
			for (int i = 0; i < params.size(); i++) {
				rawCmd.setObject(i + 1, codeValToDbVal(params.get(i))); // JDBC索引从1开始
			}
		} catch (SQLException e) {
			throw new RuntimeException("参数绑定失败", e);
		}
		return this;
	}

	// null转DBNull（同步处理）
	private Object codeValToDbVal(Object codeVal) {
		return (codeVal == null) ? null : codeVal; // Java JDBC自动处理null
	}

	// 执行查询并返回结果集（同步）
	public List<Map<String, Object>> run() {
		List<Map<String, Object>> resultList = new ArrayList<>();
		try (ResultSet rs = rawCmd.executeQuery()) {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询执行失败", e);
		}
		return resultList;
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
