package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.StatusClienteView;

public class StatusClienteTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		StatusClienteView view = new StatusClienteView();
		view.setIdStatusCliente((Long) values[0]);
		view.setDescricao((String) values[1]);

		return view;
	}
}
