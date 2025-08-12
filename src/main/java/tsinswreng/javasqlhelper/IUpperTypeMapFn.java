package tsinswreng.javasqlhelper;

import java.util.function.Function;

public interface IUpperTypeMapFn {
	Function<Object, Object> getUpperToRaw();
	void setUpperToRaw(Function<Object, Object> upperToRaw);

	Function<Object, Object> getRawToUpper();
	void setRawToUpper(Function<Object, Object> upperToRaw);
}
