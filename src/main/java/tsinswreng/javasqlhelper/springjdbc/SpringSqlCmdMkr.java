package tsinswreng.javasqlhelper.springjdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import tsinswreng.javasqlhelper.*;

import javax.sql.DataSource;


public class SpringSqlCmdMkr implements ISqlCmdMkr {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SpringSqlCmdMkr(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
	}

	@Override
	public ISqlCmd prepare(ISqlCmd cmd) {
		return cmd;
	}

	@Override
	public ISqlCmd prepare(IDbFnCtx dbFnCtx, String sql) {
		return mkCmd(dbFnCtx, sql);
	}

	@Override
	public ISqlCmd mkCmd(IDbFnCtx dbFnCtx, String sql) {
		var R = new SpringSqlCmd(namedParameterJdbcTemplate);
		R.setSql(sql);
		return R;
	}
}
