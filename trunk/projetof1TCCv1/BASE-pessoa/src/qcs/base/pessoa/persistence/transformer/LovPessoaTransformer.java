package qcs.base.pessoa.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.pessoa.persistence.view.LovPessoaView;

public class LovPessoaTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		LovPessoaView view = new LovPessoaView();
		view.setIdPessoa((Long) values[0]);
		view.setNomePessoa((String) values[1]);
		view.setCpf((String) values[2]);
		view.setCnpj((String) values[3]);
		view.setTipPes((String) values[4]);
		
		return view;
	}
}
