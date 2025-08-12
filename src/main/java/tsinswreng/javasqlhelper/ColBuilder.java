package tsinswreng.javasqlhelper;

import java.util.function.Function;

import lombok.Data;

@Data
public class ColBuilder {
	private ITable table;
	private IColumn column;
	public ColBuilder(ITable table, IColumn column){
		this.table = table;
		this.column = column;
	}

	public static ColBuilder setCol(
		ITable z
		,String nameInCode
	){
		var col = z.getColumns().get(nameInCode);
		return new ColBuilder(z, col);
	}

	public IColumn build(){
		return this.column;
	}

	public ColBuilder notNull(){
		this.column.setNotNull(true);
		return this;
	}

	public ColBuilder AdditionalSqls(Iterable<String> sqls){
		for(var sql : sqls){
			this.column.getAdditionalSqls().add(sql);
		}
		return this;
	}

	public <TRaw, TUpper> ColBuilder mapType(
		Class<TRaw> typeRaw
		,Class<TUpper> typeUpper
		,Function<TUpper, TRaw> upperToRaw
		,Function<TRaw, TUpper> rawToUpper
		,Function<Object, TRaw> objToRaw
		,Function<Object, TUpper> objToUpper
	){
		var fns = UpperTypeMapFnT.mk(upperToRaw, rawToUpper, objToRaw, objToUpper);
		return hasConv(typeRaw, typeUpper, fns);
	}

	public <TRaw, TUpper> ColBuilder mapType(
		Class<TRaw> typeRaw
		,Class<TUpper> typeUpper
		,IUpperTypeMapFnT<TRaw, TUpper> fns
	){
		var z = this;
		var col = z.column;
		col.setRawCodeType(typeRaw);
		col.setUpperCodeType(typeUpper);
		return hasConv(typeRaw, typeUpper, fns);
	}


	public <TRaw, TUpper>ColBuilder hasConv(
		Class<TRaw> typeRaw
		,Class<TUpper> typeUpper
		,IUpperTypeMapFnT<TRaw, TUpper> upperTypeMapFnT
	){
		var z = this;
		z.column.setUpperTypeMapper(
			UpperTypeMapFnT.toNonGeneric(upperTypeMapFnT)
		);
		z.table.getUpperType_dfltMapper().putIfAbsent(
			typeUpper
			,z.column.getUpperTypeMapper()
		);
		return z;
	}









}
