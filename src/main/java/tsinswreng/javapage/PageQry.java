package tsinswreng.javapage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQry implements IPageQry {
	private long pageIdx;
	private long pageSize;
	private boolean wantTotCnt;
	private long totCnt;
	public static IPageQry slctAll(){
		var R = new PageQry();
		R.setPageIdx(0);
		R.setPageSize(Long.MAX_VALUE);
		R.setWantTotCnt(true);
		return R;
	}
}
