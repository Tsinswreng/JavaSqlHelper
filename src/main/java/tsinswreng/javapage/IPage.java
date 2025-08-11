package tsinswreng.javapage;

public interface IPage<T> extends IPageInfo, I_totCnt, I_hasTotCnt {
	public Iterable<T> getData();
}


