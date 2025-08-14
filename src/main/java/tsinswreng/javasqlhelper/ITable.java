package tsinswreng.javasqlhelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tsinswreng.dictmapper.IDictMapperShallow;


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


	//----extn methods
	public default String qt(String s){
		var z = this;
		return z.getSqlMkr().quote(s);
	}

	public default String colNameToDb(String codeColName){
		var z = this;
		return z.getDbColName_codeColName().get(codeColName);
	}

	public default String fld(String coldeColName){
		var z = this;
		return z.qt(
			z.colNameToDb(coldeColName)
		);
	}

	public default IParam prm(String name){
		var z = this;
		return z.getSqlMkr().param(name);
	}

	public default Map<String, Object> toCodeDict(Map<String, Object> dbDict){
		var z = this;
		Map<String, Object> R = new LinkedHashMap<>();
		for(var kv : dbDict.entrySet()){
			var kDb = kv.getKey();
			var vDb = kv.getValue();
			var kCode = z.getDbColName_codeColName().get(kDb);
			var colCode = z.getColumns().get(kCode);
			var vCode = vDb;
			if(colCode.getUpperTypeMapper() != null
				&& colCode.getUpperTypeMapper().getRawToUpper()!= null
			){
				colCode.getUpperTypeMapper().getRawToUpper().apply(vDb);
			}
			R.put(kCode, vCode);
		}
		return R;
	}

	public default <T> T assignEntity(
		Map<String, Object> dbDict
		,T toBeAssigned
		,Class<T> entityType
	){
		var z = this;
		var codeDict = z.toCodeDict(dbDict);
		z.getDictMapper().assignShallow(entityType, z, codeDict);
		return toBeAssigned;
	}

	public default <T> T assignEntity(
		Map<String, Object> dbDict
		,T toBeAssigned
	){
		var z = this;
		var entityType = z.getCodeEntityType();
		var codeDict = z.toCodeDict(dbDict);
		z.getDictMapper().assignShallow(entityType, z, codeDict);
		return toBeAssigned;
	}

	public default <TPo> TPo dbDictToEntity(
		Map<String, Object> dbDict
		,Class<TPo> entityType
		,TPo R
	){
		var z = this;
		var codeDict = z.toCodeDict(dbDict);
		z.getDictMapper().assignShallow(entityType, R, codeDict);
		return R;
	}

}
