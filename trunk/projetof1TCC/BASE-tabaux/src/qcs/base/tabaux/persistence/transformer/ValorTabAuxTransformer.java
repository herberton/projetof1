package qcs.base.tabaux.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.tabaux.persistence.view.ValorTabAuxView;

public class ValorTabAuxTransformer implements ResultTransformer{	

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}
	
	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		ValorTabAuxView view = new ValorTabAuxView();
		view.setIdValTabAux((Long)values[0]);
		view.setNomeTabAux((String)values[1]);
		view.setNome((String)values[2]);
		view.setObservacao((String) values[3]);
		return view;
	}
	
	
	
}