package tsinswreng.javapage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Page<T> implements IPage<T> {

	public IPageQry getPageQry(){
		return PageQry.builder()
		.pageIdx(pageIdx)
		.pageSize(pageSize)
		.wantTotCnt(wantTotCnt)
		.build();
	}

	public void setPageQry(IPageQry v){
		if(v == null){return;}
		pageIdx = v.getPageIdx();
		pageSize = v.getPageSize();
		wantTotCnt = v.isWantTotCnt();
	}

	private boolean wantTotCnt;

	private boolean hasTotCnt;

	private long totCnt;

	private long pageIdx;

	private long pageSize;

	private Iterable<T> data;

	public long offset(){
		return pageIdx * pageSize;
	}
}
