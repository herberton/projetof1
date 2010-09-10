package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.FuncionalidadeView;

public class FuncionalidadeTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		FuncionalidadeView view = new FuncionalidadeView();
		view.setIdFuncionalidade((Long) values[0]);
		view.setNomeFuncionalidade((String) values[1]);
		view.setDescricaoFuncionalidade((String) values[2]);
		view.setVisivel("S".equalsIgnoreCase((String) values[3]));
		view.setModulo((String) values[4]);

		return view;
	}
}
