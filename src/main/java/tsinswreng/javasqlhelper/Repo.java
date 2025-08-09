package tsinswreng.javasqlhelper;

import lombok.Getter;
import lombok.Setter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
public   class Repo<TEntity, TId> implements IRepo<TEntity, TId> {
    private ITblMgr tblMgr;
    private ISqlCmdMkr sqlCmdMkr;
    private IDictMapperShallow dictMapper;

    public Repo(ITblMgr tblMgr, ISqlCmdMkr sqlCmdMkr, IDictMapperShallow dictMapper) {
        this.tblMgr = tblMgr;
        this.sqlCmdMkr = sqlCmdMkr;
        this.dictMapper = dictMapper;
    }

    @Override
    public CompletableFuture<Function<Object, CompletableFuture<Long>>> fnCount(Object ctx, Object ct) {
        // 這裡僅為結構示例，具體 SQL 操作需根據 Java SQL API 實現
        return CompletableFuture.completedFuture((c) -> CompletableFuture.completedFuture(0L));
    }

    @Override
    public CompletableFuture<BiFunction<List<TEntity>, Object, CompletableFuture<Void>>> fnInsertMany(Object ctx, Object ct) {
        return CompletableFuture.completedFuture((entities, c) -> CompletableFuture.completedFuture(null));
    }

    @Override
    public CompletableFuture<BiFunction<TId, Object, CompletableFuture<TEntity>>> fnSelectById(Object ctx, Object ct) {
        return CompletableFuture.completedFuture((id, c) -> CompletableFuture.completedFuture(null));
    }

    @Override
    public CompletableFuture<BiFunction<List<IdDict<TId>>, Object, CompletableFuture<Void>>> fnUpdateManyById(Object ctx, Map<String, Object> modelDict, Object ct) {
        return CompletableFuture.completedFuture((idDicts, c) -> CompletableFuture.completedFuture(null));
    }
}
