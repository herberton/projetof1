package qcs.base.seguranca.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;

public class PerfilFuncionalidadeTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		PerfilFuncionalidadeView view = new PerfilFuncionalidadeView();
		view.setIdPerfilFunc((Long) values[0]);
		view.setIdPerfil((Long) values[1]);
		view.setNomePerfil((String) values[2]);
		view.setIdFuncionalidade((Long) values[3]);
		view.setNomeFuncionalidade((String) values[4]);
		view.setAtivo((String) values[5]);

		return view;
	}
}
