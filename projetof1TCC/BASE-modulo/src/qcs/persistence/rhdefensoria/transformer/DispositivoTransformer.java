package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.DispositivoView;

public class DispositivoTransformer implements ResultTransformer{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliasses) {
		
		DispositivoView view = new DispositivoView();
	
		view.setIdDispositivo((Long)values[0]);
		view.setIp((String)values[1]);
		view.setDescricaoStatusDispositivo((String)values[2]);
		
		return view;
		
	}

}
