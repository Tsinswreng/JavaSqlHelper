package tsinswreng.javasqlhelper.sample;

import lombok.Data;

@Data
public class PoBase implements IPoBase{

	private Long createdAt = System.currentTimeMillis();
	private Long updatedAt;
	private EPoStatus status = EPoStatus.NORMAL;

}
