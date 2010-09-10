package qcs.base.enderecamento.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.enderecamento.persistence.view.LogradouroView;

public class LogradouroTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		LogradouroView view = new LogradouroView();
		view.setIdLogradouro((Long) values[0]);
		view.setCep((String)values[1]);
		view.setLogradouro((String) values[2]);
		view.setBairro((String) values[3]);
		view.setCidade((String) values[4]);
		view.setUf((String) values[5]);
		view.setInativo((String) values[6]);
		
		return view;
	}
}
