package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.ListOfValuesModuloSistemaView;

public class ListOfValuesModuloSistemaTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		ListOfValuesModuloSistemaView view = new ListOfValuesModuloSistemaView();
		view.setIdModulo((Long)values[0]);
		view.setNome((String)values[1]);
		view.setDescritivo((String)values[2]);
		
		return view;
	}
}
