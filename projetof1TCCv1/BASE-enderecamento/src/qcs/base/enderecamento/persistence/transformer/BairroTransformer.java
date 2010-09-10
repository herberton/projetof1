package qcs.base.enderecamento.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.enderecamento.persistence.view.BairroView;

public class BairroTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		BairroView view = new BairroView();
		view.setIdBairro((Long)values[0]);
		view.setNome((String)values[1]);
		view.setAbrev((String)values[2]);
		view.setNomeCidade((String)values[3]);

		return view;
	}
}
