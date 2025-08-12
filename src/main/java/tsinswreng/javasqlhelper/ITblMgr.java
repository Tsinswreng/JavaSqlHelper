package tsinswreng.javasqlhelper;

import java.util.Map;

public interface ITblMgr {
	Map<Class<?>, ITable> getEntityType_Tbl();
	void getEntityType_Tbl(Map<Class<?>, ITable> typeTable);

	String getDbSrcType();
	void setDbSrcType(String dbSrcType);

	ISqlMkr getSqlMkr();
	void setSqlMkr(ISqlMkr sqlMkr);

	default void addTable(ITable tbl) {
		tbl.setSqlMkr(getSqlMkr());
		getEntityType_Tbl().put(tbl.getCodeEntityType(), tbl);
	}

	default ITable getTbl(Class<?> poClass) {
		return getEntityType_Tbl().get(poClass);
	}
}
