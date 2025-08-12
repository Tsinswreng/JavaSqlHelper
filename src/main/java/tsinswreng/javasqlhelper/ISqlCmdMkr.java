package tsinswreng.javasqlhelper;

public interface ISqlCmdMkr {
	ISqlCmd prepare(ISqlCmd cmd);
	ISqlCmd prepare(IDbFnCtx dbFnCtx, String sql);
	ISqlCmd mkCmd(IDbFnCtx dbFnCtx, String sql);
	//private Map<IParam, Integer> param_pos = new LinkedHashMap<>();
}
