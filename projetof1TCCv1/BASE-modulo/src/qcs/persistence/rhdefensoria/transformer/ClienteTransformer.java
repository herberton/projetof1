package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.ClienteView;

public class ClienteTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		ClienteView view = new ClienteView();
		view.setIdCliente((Long) values[0]);
		view.setNome((String) values[1]);
		view.setRg((String) values[2]);
		view.setCpf((String) values[3]);
		view.setCelular((Integer) values[4]);		
		view.setdispositivoAssoc((Long)values[5]);

		return view;
	
	}
}
