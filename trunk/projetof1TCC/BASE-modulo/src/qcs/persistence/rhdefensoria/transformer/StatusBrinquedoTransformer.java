package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;

public class StatusBrinquedoTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		StatusBrinquedoView view = new StatusBrinquedoView();
		view.setIdStatusBrinquedo((Long) values[0]);
		view.setDescricao((String) values[1]);

		return view;
	}
}
