package tsinswreng.javasqlhelper;

import java.util.List;
import java.util.Map;

public interface ISqlCmd {
	String getSql();
	void setSql(String sql);

	Iterable<Map<String, Object>> run();

	ISqlCmd rawArgs(Map<String, Object> args);

	ISqlCmd resolvedArgs(Map<String, Object> args);

	ISqlCmd args(List<Object> args);

	ISqlCmd withCtx(IDbFnCtx dbFnCtx);
}
