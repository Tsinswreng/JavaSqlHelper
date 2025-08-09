package tsinswreng.javasqlhelper;

public interface IDbFnCtx {
	Transaction getTxn();
}

interface Transaction {
	int getIsolationLevel(); // 如Connection.TRANSACTION_READ_COMMITTED
}
