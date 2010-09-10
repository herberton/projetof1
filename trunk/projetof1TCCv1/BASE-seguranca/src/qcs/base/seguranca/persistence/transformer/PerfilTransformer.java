package qcs.base.seguranca.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.seguranca.persistence.view.PerfilView;

public class PerfilTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		PerfilView view = new PerfilView();
		view.setIdPerfil((Long) values[0]);
		view.setNomePerfilAcesso((String) values[1]);
		view.setDescPerfilAcesso((String) values[2]);
		view.setAtivo((String) values[3]);
		return view;
	}
}
