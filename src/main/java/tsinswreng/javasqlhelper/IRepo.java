package tsinswreng.javasqlhelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface IRepo<TEntity, TId> {
	CompletableFuture<BiFunction<List<TEntity>, Object, CompletableFuture<Void>>> fnInsertMany(Object ctx, Object ct);

	CompletableFuture<Function<Object, CompletableFuture<Long>>> fnCount(Object ctx, Object ct);

	CompletableFuture<BiFunction<TId, Object, CompletableFuture<TEntity>>> fnSelectById(Object ctx, Object ct);

	CompletableFuture<BiFunction<List<IdDict<TId>>, Object, CompletableFuture<Void>>> fnUpdateManyById(Object ctx,
			Map<String, Object> modelDict, Object ct);
}
