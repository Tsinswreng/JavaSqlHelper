package tsinswreng.javasqlhelper.jdbc;
import tsinswreng.javasqlhelper.IDbFnCtx;
import tsinswreng.javasqlhelper.ISqlCmd;
import tsinswreng.javasqlhelper.ISqlCmdMkr;

// public class JdbcSqlCmdMkr implements ISqlCmdMkr {
// 	private final IDbConnection dbConnection;  // 数据库连接依赖 [[6]]
// 	private final Map<IParam, Integer> param_pos = new LinkedHashMap<>();  // 参数位置映射
// 	public ISqlCmd prepare(ISqlCmd cmd){
// 		return cmd;
// 	}

// 	public ISqlCmd prepare(IDbFnCtx dbFnCtx, String sql){

// 	}

// 	public ISqlCmd mkCmd(IDbFnCtx dbFnCtx, String sql){

// 	}

// }

import javax.sql.DataSource;

import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class JdbcSqlCmdMkr implements ISqlCmdMkr {
	private final DataSource dataSource; // 依赖注入的 DataSource
	private Connection conn;

	public JdbcSqlCmdMkr(
		DataSource dataSource
		,Connection conn
	) {
		this.dataSource = dataSource;
		this.conn = conn;
	}

	@Override
	public ISqlCmd prepare(ISqlCmd cmd) {

		return cmd;
	}

	@Override
	public ISqlCmd prepare(IDbFnCtx dbFnCtx, String sql) {
		try {
			PreparedStatement ps = conn.prepareStatement(sql); // 预编译 SQL
			return new JdbcSqlCmd(ps); // 返回封装对象
		} catch (SQLException e) {
			throw new RuntimeException("SQL 预编译失败", e);
		}
	}

	@Override
	public ISqlCmd mkCmd(IDbFnCtx dbFnCtx, String sql) {
		return prepare(dbFnCtx, sql);
	}

}
