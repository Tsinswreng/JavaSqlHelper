package tsinswreng.javasqlhelper.sqlite;

import java.util.HashMap;
import java.util.Map;

import tsinswreng.javasqlhelper.ISqlTypeMapper;
import tsinswreng.javasqlhelper.TypeEnums;

public class SqliteTypeMapper implements ISqlTypeMapper {
	private static SqliteTypeMapper inst = null;
	public static SqliteTypeMapper getInst(){
		if(inst == null){
			inst = new SqliteTypeMapper();
		}
		return inst;
	}

	public Map<Class<?>, String> codeType_name = new HashMap<>();
	public SqliteTypeMapper(){
		var d = codeType_name;
		d.put(TypeEnums.BYTE, "INTEGER");
		d.put(TypeEnums.SHORT, "INTEGER");
		d.put(TypeEnums.INTEGER, "INTEGER");
		d.put(TypeEnums.LONG, "INTEGER");
		d.put(TypeEnums.FLOAT, "REAL");
		d.put(TypeEnums.DOUBLE, "REAL");
		d.put(TypeEnums.STRING, "TEXT");
		d.put(TypeEnums.BYTE_ARR, "BLOB");
	}

	public String ToDbTypeName(Class<?> Type){
		if(codeType_name.containsKey(Type)){
			return codeType_name.get(Type);
		}else{
			throw new RuntimeException("Type Mapping Error: "+Type);
		}
	}
}
