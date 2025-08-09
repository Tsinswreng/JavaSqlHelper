package tsinswreng.javasqlhelper;

import java.util.List;
import java.util.Map;

public interface ISqlCmd {
	String getSql();
	void setSql(String sql);

	List<Map<String, Object>> run(Object ct);

	ISqlCmd args(Map<String, Object> args);

	ISqlCmd args(List<Object> args);

	ISqlCmd withCtx(IDbFnCtx dbFnCtx);
}
