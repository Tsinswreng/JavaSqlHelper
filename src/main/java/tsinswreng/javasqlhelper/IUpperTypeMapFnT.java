package tsinswreng.javasqlhelper;

import java.util.function.Function;

public interface IUpperTypeMapFnT<TRaw, TUpper> {

	public Function<TUpper, TRaw> getUpperToRaw();
	public void setUpperToRaw(Function<TUpper, TRaw> v);

	public Function<TRaw, TUpper> getRawToUpper();
	public void setRawToUpper(Function<TRaw, TUpper> v);

	public Function<Object, TRaw> getObjToRaw();
	public void setObjToRaw(Function<Object, TRaw> v);

	public Function<Object, TUpper> getObjToUpper();
	public void setObjToUpper(Function<Object, TUpper> v);
}
