package tsinswreng.javasqlhelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Table implements ITable{
	public Table(){}
	public Table(
		IDictMapperShallow dictMapper
		,String name
		,Map<String, Class<?>> codeCol_UpperType
	){
		this.dictMapper = dictMapper;
		this.dbTblName = name;
		this.codeCol_upperType = codeCol_UpperType;
	}

	private IDictMapperShallow dictMapper;
	private String dbTblName = "";
	private Map<String, IColumn> columns = new LinkedHashMap<>();
	private String codeIdName = "";
	private Map<String, String> dbColName_codeColName = new LinkedHashMap<>();
	private Map<String, Class<?>> codeCol_upperType = new LinkedHashMap<>();
	private List<String> innerAdditionalSqls = new ArrayList<>();
	private List<String> outerAdditionalSqls = new ArrayList<>();
	private Map<Class<?>, IUpperTypeMapFn> upperType_dfltMapper = new LinkedHashMap<>();
	private ISoftDeleteCol softDelCol;
	private Class<?> codeEntityType;
	private ISqlMkr sqlMkr;

	private boolean _inited = false;
	public Table Init(){
		if(_inited){
			return this;
		}
		return this;
	}

	private static String _oneCol(ITable tbl, IColumn col){
		var z = tbl;
		var R = new ArrayList<String>();
		R.add(tbl.qt(col.getDbName()));
		if(col.getDbType() != null && !col.getDbType().equals("")){
			R.add(col.getDbType());
		}else{
			if(col.getRawCodeType() == null){
				throw new RuntimeException("No db type for col: "+col.getDbName());
			}
			try {
				var dbTypeName = z.getSqlMkr().getSqlTypeMapper().ToDbTypeName(col.getRawCodeType());
				R.add(dbTypeName);
			} catch (Exception e) {
				throw new RuntimeException("Type Mapping Error for Column: "+col.getDbName(), e);
			}
		}
		if(col.getAdditionalSqls() != null){
			R.addAll(col.getAdditionalSqls());
		}
		if(col.isNotNull()){
			R.add("NOT NULL");
		}
		return String.join(" ", R);
	}

	private static String _fmtInnerSqls(ITable z, List<String> sqls){
		if(sqls == null || sqls.size() == 0){
			return "";
		}
		var sqlList = z.getInnerAdditionalSqls();
		if(sqlList == null){
			sqlList = new ArrayList<String>();
		}
		return ",\n\t"+String.join("\n\t", sqlList);
	}
	private static String _fmtOuterSqls(ITable z, List<String> sqls){
		if(sqls == null || sqls.size() == 0){
			return "";
		}
		var R = new ArrayList<String>();
		for(var sql : sqls){
			R.add(sql);
			R.add(";\n");
		}
		return String.join("", R);
	}

	public String sqlMkTbl(ITable z){
		var lines = new ArrayList<String>();
		for(var name_col : z.getColumns().entrySet()){
			//var name = name_col.getKey();
			var col = name_col.getValue();
			lines.add(_oneCol(z, col));
		}
		var S =
"CREATE TABLE IF NOT EXISTS" + z.qt(z.getDbTblName()) + "("
+"	"+String.join(",\n\t", lines)+_fmtInnerSqls(z, z.getInnerAdditionalSqls())
+");"
+_fmtOuterSqls(z, z.getOuterAdditionalSqls());
		return S;
	}



}
