package tsinswreng.javasqlhelper;

import java.util.List;
import java.util.Map;

public interface ITable {
	IDictMapperShallow getDictMapper();
	void setDictMapper(IDictMapperShallow dictMapper);

	Class<?> getCodeEntityType();
	void setCodeEntityType(Class<?> type);

	String getDbTblName();
	void setDbTblName(String name);

	Map<String, IColumn> getColumns();
	void setColumns(Map<String, IColumn> columns);

	String getCodeIdName();
	void setCodeIdName(String name);

	ISoftDeleteCol getSoftDelCol();
	void setSoftDelCol(ISoftDeleteCol col);

	Map<String, String> getDbColName_codeColName();
	void setDbColName_codeColName(Map<String, String> map);

	Map<String, Class<?>> getCodeCol_upperType();
	void setCodeCol_upperType(Map<String, Class<?>> map);

	ISqlMkr getSqlMkr();
	void setSqlMkr(ISqlMkr sqlMkr);

	Map<Class<?>, IUpperTypeMapFn> getUpperType_dfltMapper();
	void setUpperType_dfltMapper(Map<Class<?>, IUpperTypeMapFn> map);

	List<String> getInnerAdditionalSqls();
	void setInnerAdditionalSqls(List<String> sqls);

	List<String> getOuterAdditionalSqls();
	void setOuterAdditionalSqls(List<String> sqls);

}
