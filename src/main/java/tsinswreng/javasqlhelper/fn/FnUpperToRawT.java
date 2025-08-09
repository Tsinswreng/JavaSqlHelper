package tsinswreng.javasqlhelper.fn;

@FunctionalInterface
public interface FnUpperToRawT<TRaw, TUpper> {
	TRaw upperToRaw(TUpper upper);
}
