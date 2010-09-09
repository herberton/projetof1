package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.ModuloSistemaView;

public class ModuloSistemaTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		ModuloSistemaView view = new ModuloSistemaView();
		view.setIdModulo((Long)values[0]);
		view.setNomeModulo((String)values[1]);
		view.setPosicao((Integer)values[2]);
		view.setDescricao((String)values[3]);
		view.setPrincipal("S".equalsIgnoreCase((String)values[4]));
		return view;
	}
}
