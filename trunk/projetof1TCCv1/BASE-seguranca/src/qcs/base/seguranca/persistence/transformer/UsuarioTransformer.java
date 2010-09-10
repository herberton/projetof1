package qcs.base.seguranca.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.seguranca.persistence.view.UsuarioView;

public class UsuarioTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		UsuarioView view = new UsuarioView();
		view.setIdUsuario((Long)values[0]);
		view.setNomePessoa((String)values[1]);
		view.setLogin((String)values[2]);
		view.setCpf((String)values[3]);
		view.setCnpj((String)values[4]);
		view.setBloqueado(((String)values[5]).equalsIgnoreCase("S"));
		view.setAtivo(((String)values[6]).equalsIgnoreCase("S"));
		return view;
	}
}
