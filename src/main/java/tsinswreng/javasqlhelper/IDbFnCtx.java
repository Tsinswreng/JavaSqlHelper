package tsinswreng.javasqlhelper;

import java.util.Collection;
import java.util.Map;

public interface IDbFnCtx extends AutoCloseable{
	Transaction getTxn();
	Map<String, Object> getProps();
	Collection<Object> getObjsToDispose();

	default void addObjToDispose(Object obj) {
		if(getObjsToDispose() == null){
			return;
		}
		getObjsToDispose().add(obj);
	}

	@Override
	default void close() throws Exception {
		if(getObjsToDispose() == null){
			return;
		}
		for(Object obj : getObjsToDispose()){
			if(obj instanceof AutoCloseable){
				((AutoCloseable)obj).close();
			}
		}
	}
}

interface Transaction {
	int getIsolationLevel(); // 如Connection.TRANSACTION_READ_COMMITTED
}
