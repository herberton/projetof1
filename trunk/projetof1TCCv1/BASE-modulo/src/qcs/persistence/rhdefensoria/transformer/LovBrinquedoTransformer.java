package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.LovBrinquedoView;

public class LovBrinquedoTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		LovBrinquedoView view = new LovBrinquedoView();
		view.setIdBrinquedo((Long) values[0]);
		view.setNomeBrinquedo((String) values[1]);
		view.setObservacao((String) values[2]);
		
		return view;
	}
}
