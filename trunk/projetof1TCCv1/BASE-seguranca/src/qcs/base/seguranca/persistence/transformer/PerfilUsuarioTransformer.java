package qcs.base.seguranca.persistence.transformer;

import java.util.Date;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.seguranca.persistence.view.PerfilUsuarioView;

public class PerfilUsuarioTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		PerfilUsuarioView view = new PerfilUsuarioView();

		view.setIdPerfilUsuario((Long) values[0]);
		view.setAtivo("S".equalsIgnoreCase((String) values[1]));
		view.setDataAtivacao((Date) values[2]);
		view.setDataDesativacao((Date) values[3]);
		view.setDataUltAlteracao((Date) values[4]);
		view.setObservacao((String) values[5]);
		view.setUsuario((String)values[6]);
		view.setPerfil((String)values[7]);
		view.setUsuarioAtiva((String)values[8]);
		view.setUsuarioDesativa((String)values[9]);
		view.setUsuarioUltAlt((String)values[10]);
		
		return view;
	}
}
