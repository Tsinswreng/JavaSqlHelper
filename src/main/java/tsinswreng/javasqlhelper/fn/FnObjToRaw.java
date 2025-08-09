package tsinswreng.javasqlhelper.fn;

@FunctionalInterface
public interface FnObjToRaw<TRaw> {
	TRaw objToRaw(Object obj);
}
