package qcs.base.tabaux.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;

public class TabelaAuxiliarTransformer implements ResultTransformer{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}
	
	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		TabelaAuxiliarView view = new TabelaAuxiliarView();
		view.setIdTabAux((Long) values[0]);
		view.setNome((String)values[1]);
		view.setDescricao((String) values[2]);
		return view;
	}
	
	
	
}