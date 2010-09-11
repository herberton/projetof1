package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.BrinquedoView;

public class BrinquedoTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		BrinquedoView view = new BrinquedoView();
		view.setIdBrinquedo((Long) values[0]);
		view.setNome((String) values[1]);
		view.setTempoMensagem((Integer) values[2]);
		view.setEstimativaTempoFila((Integer) values[3]);
		view.setQntFilaFisica((Integer) values[4]);
		view.setQntMaxFilaFisica((Integer) values[5]);
		view.setStatusBrinquedo((Long) values[6]);
		view.setDescricaoStatusBrinquedo((String) values[7]);

		return view;
	}
}
