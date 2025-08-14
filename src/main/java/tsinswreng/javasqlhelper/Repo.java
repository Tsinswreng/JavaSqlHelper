package tsinswreng.javasqlhelper;

import lombok.Data;
import tsinswreng.dictmapper.IDictMapperShallow;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

@Data//implements IRepo<TEntity, TId>
public class Repo<TEntity, TId>{
	private Class<?> entityType;
	private Class<?> idType;
	private ITblMgr tblMgr;
	private ISqlCmdMkr sqlCmdMkr;
	private IDictMapperShallow dictMapper;

	public Repo(ITblMgr tblMgr, ISqlCmdMkr sqlCmdMkr, IDictMapperShallow dictMapper) {
		this.tblMgr = tblMgr;
		this.sqlCmdMkr = sqlCmdMkr;
		this.dictMapper = dictMapper;
	}

	public Function<TId, TEntity> fnSlctById(
		IDbFnCtx ctx
	){
		var t = tblMgr.getTbl(entityType);
		var sql = "SELECT * FROM" + t.qt(t.getDbTblName()) + "WHERE"+t.fld(t.getCodeIdName())+"?";
		var sqlCmd = sqlCmdMkr.mkCmd(ctx, sql);
		ctx.addObjToDispose(sqlCmd);
		return (id)->{
			var idCol = t.getColumns().get(t.getCodeIdName());
			//TODO
			return null;
		};


	}
}
