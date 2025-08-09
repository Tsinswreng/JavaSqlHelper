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
}
