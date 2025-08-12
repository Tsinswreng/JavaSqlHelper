package tsinswreng.javasqlhelper.sample;

/**
 * 成員ʸ皆用引用類型
 */
public interface IPoBase {
	public static String N_CREATED_AT = "createdAt";
	public static String N_UPDATED_AT = "updatedAt";
	public static String N_STATUS = "status";

	public Long getCreatedAt();
	public void setCreatedAt(Long v);

	public Long getUpdatedAt();
	public void setUpdatedAt(Long v);

	public EPoStatus getStatus();
	public void setStatus(EPoStatus v);

}
