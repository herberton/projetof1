package qcs.base.enderecamento.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.enderecamento.persistence.view.CidadeView;

public class CidadeTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		CidadeView view = new CidadeView();
		view.setIdCidade((Long) values[0]);
		view.setNome((String) values[1]);
		view.setUf((String) values[2]);
		view.setSelecionado(false);

		return view;
	}
}
