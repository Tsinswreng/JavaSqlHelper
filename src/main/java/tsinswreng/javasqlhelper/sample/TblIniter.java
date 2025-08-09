package tsinswreng.javasqlhelper.sample;

import java.util.List;

import tsinswreng.javasqlhelper.ColBuilder;
import tsinswreng.javasqlhelper.ITable;

public class TblIniter {

	private void cfgPoBase(ITable tbl){
		var o = tbl;
		o.setCodeIdName(I_Id.N_ID);
		ColBuilder.setCol(o, I_Id.N_ID).AdditionalSqls(List.of("PRIMARY KEY"));
		ColBuilder.setCol(o, IPoBase.N_STATUS).<Integer, EPoStatus>mapType(
			Integer.class, EPoStatus.class
			,(v)->v.toInt()
			,(v)->EPoStatus.fromInt(v)
			,null,null
		);
	}


	public void init(){

	}

}
