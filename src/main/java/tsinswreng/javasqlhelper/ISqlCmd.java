package tsinswreng.javasqlhelper;

import java.util.List;
import java.util.Map;

public interface ISqlCmd extends AutoCloseable{
	String getSql();
	void setSql(String sql);

	Iterable<Map<String, Object>> run();

	/// <summary>
	/// raw arg name to value
	/// {
	/// 	"arg1": val1,
	/// 	"arg2": val2
	/// }
	/// </summary>
	/// <param name="Args"></param>
	/// <returns></returns>

	ISqlCmd rawArgs(Map<String, Object> args);
	/// <summary>
	/// resolved arg string(with prefix, e.g ":" for oracle) to value
	/// {
	/// 	":arg1": val1,
	/// 	":arg2": val2
	/// }
	/// </summary>
	/// <param name="Args"></param>
	/// <returns></returns>
	ISqlCmd resolvedArgs(Map<String, Object> args);

	ISqlCmd args(List<Object> args);

	ISqlCmd withCtx(IDbFnCtx dbFnCtx);
}
