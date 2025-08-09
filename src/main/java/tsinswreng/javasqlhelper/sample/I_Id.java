package tsinswreng.javasqlhelper.sample;

public interface I_Id<T> {
	public static String N_ID = "id";
	T getId();
	void setId(T v);
}
