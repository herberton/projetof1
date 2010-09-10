package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.CatracaView;

public class CatracaTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		CatracaView view = new CatracaView();
		view.setIdCatraca((Long) values[0]);
		view.setDescricao((String) values[1]);
		view.setLocalizacao((String) values[2]);
		view.setNomeBrinquedo((String) values[3]);

		return view;
	}
}
