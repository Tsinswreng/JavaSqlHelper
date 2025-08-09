package tsinswreng.javasqlhelper;

import java.util.function.Function;

import lombok.Data;

@Data
public class UpperTypeMapFnT<TRaw, TUpper> implements IUpperTypeMapFnT<TRaw, TUpper>{
	private Function<TUpper, TRaw> upperToRaw;
	private Function<TRaw, TUpper> rawToUpper;
	private Function<Object, TRaw> objToRaw;
	private Function<Object, TUpper> objToUpper;

	public static <TRaw, TUpper>IUpperTypeMapFnT<TRaw, TUpper> mk(
	 	Function<TUpper, TRaw> fnUpperToRaw,
		Function<TRaw, TUpper> fnRawToUpper,
		Function<Object, TRaw> fnObjToRaw,
		Function<Object, TUpper> fnObjToUpper
	){
		var R = new UpperTypeMapFnT<TRaw, TUpper>();
		R.setUpperToRaw(fnUpperToRaw);
		R.setRawToUpper(fnRawToUpper);
		R.setObjToRaw(fnObjToRaw);
		R.setObjToUpper(fnObjToUpper);
		return R;
	}

	public static <TRaw, TUpper>IUpperTypeMapFnT<TRaw, TUpper> mk(
	 	Function<TUpper, TRaw> fnUpperToRaw,
		Function<TRaw, TUpper> fnRawToUpper
	){
		var R = new UpperTypeMapFnT<TRaw, TUpper>();
		R.setUpperToRaw(fnUpperToRaw);
		R.setRawToUpper(fnRawToUpper);
		R.setObjToRaw(null);
		R.setObjToUpper(null);
		return R;
	}

	public static <TRaw, TUpper>IUpperTypeMapFn toNonGeneric(
		IUpperTypeMapFnT<TRaw, TUpper> z
	){
		var UpperToRaw = z.getUpperToRaw();
		var RawToUpper = z.getRawToUpper();
		var ObjToRaw = z.getObjToRaw();
		var ObjToUpper = z.getObjToUpper();
		var R = new UpperTypeMapFn();
		R.setUpperToRaw((x)->{
			try{
				if(UpperToRaw == null){return x;}
				if(ObjToUpper != null){
					return UpperToRaw.apply(ObjToUpper.apply(x));
				}
				return UpperToRaw.apply((TUpper)x);
			}
			catch (Exception e){
				//System.err.println();
				throw e;
			}
		});

		R.setRawToUpper((x)->{
			try{
				if(RawToUpper == null){return x;}
				if(ObjToRaw != null){
					return RawToUpper.apply(ObjToRaw.apply(x));
				}
				return RawToUpper.apply((TRaw)x);
			}
			catch (Exception e){
				//System.err.println();
				throw e;
			}
		});
		return R;
	}
}
