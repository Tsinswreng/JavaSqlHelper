package tsinswreng.javasqlhelper;

public interface ISqlMkr {
	ISqlTypeMapper getSqlTypeMapper();
	void setSqlTypeMapper(ISqlTypeMapper mapper);

	String quote(String name);
	String prmStr(String name);
	IParam param(String name);
	String limitOffset(String limit, String offset);
}
