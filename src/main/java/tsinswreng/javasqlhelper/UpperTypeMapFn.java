package tsinswreng.javasqlhelper;

import lombok.Data;
import tsinswreng.javasqlhelper.fn.*;

@Data
public class UpperTypeMapFn implements IUpperTypeMapFn{
	private FnUpperToRaw upperToRaw;
	private FnRawToUpper rawToUpper;
}
