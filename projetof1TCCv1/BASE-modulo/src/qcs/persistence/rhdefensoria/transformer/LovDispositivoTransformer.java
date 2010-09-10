package qcs.persistence.rhdefensoria.transformer;

import java.util.Date;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.LovDispositivoView;

public class LovDispositivoTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		LovDispositivoView view = new LovDispositivoView();
		view.setIdDispositivo((Long) values[0]);
		view.setStatusDispositivo((String) values[1]);
		view.setDataCadastro((Date) values[2]);
		view.setIp((String) values[3]);		
		
		return view;	
	
	}
}
