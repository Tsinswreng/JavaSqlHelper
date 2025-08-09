package tsinswreng.javasqlhelper;

public interface ISqlCmdMkr {
    ISqlCmd prepare(ISqlCmd cmd, Object ct);
    ISqlCmd prepare(Object dbFnCtx, String sql, Object ct);
    ISqlCmd mkCmd(Object dbFnCtx, String sql, Object ct);
}
