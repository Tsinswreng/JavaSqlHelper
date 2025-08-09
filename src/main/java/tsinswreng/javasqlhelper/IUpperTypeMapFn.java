package tsinswreng.javasqlhelper;

import tsinswreng.javasqlhelper.fn.*;

public interface IUpperTypeMapFn {
	FnUpperToRaw getUpperToRaw();
	void setUpperToRaw(FnUpperToRaw upperToRaw);

	FnRawToUpper getRawToUpper();
	void setRawToUpper(FnRawToUpper upperToRaw);
}
