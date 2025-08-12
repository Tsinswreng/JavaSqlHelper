package tsinswreng.javasqlhelper;

import java.util.List;


public interface IColumn {
	String getDbName();
	void setDbName(String name);

	String getDbType();
	void setDbType(String type);

	Class<?> getRawCodeType();
	void setRawCodeType(Class<?> type);

	Class<?> getUpperCodeType();
	void setUpperCodeType(Class<?> type);

	List<String> getAdditionalSqls();
	void setAdditionalSqls(List<String> sqls);

	IUpperTypeMapFn getUpperTypeMapper();
	void setUpperTypeMapper(IUpperTypeMapFn upperTypeMapFn);

	boolean isNotNull();
	void setNotNull(boolean v);

}
