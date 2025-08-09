package tsinswreng.javasqlhelper.sample;

public enum EPoStatus {
	NORMAL, DELETED, DISABLED;

	public int toInt(){
		return this.ordinal();
	}
	public Integer toInteger(){
		return this.ordinal();
	}
	public static EPoStatus fromInt(int v){
		return EPoStatus.values()[v];
	}
}
