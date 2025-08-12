package tsinswreng.javasqlhelper;

import java.util.function.Function;

import lombok.Data;


@Data
public class UpperTypeMapFn implements IUpperTypeMapFn{
	private Function<Object, Object> upperToRaw;
	private Function<Object, Object> rawToUpper;
}
