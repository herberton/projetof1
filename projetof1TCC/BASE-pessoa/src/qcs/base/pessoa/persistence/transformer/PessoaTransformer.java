package qcs.base.pessoa.persistence.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.base.pessoa.persistence.view.PessoaView;

public class PessoaTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		PessoaView view = new PessoaView();
		view.setIdPessoa((Long)values[0]);
		view.setNomePessoa((String)values[1]);
		view.setCnpj((String)values[2]);
		view.setCpf((String)values[3]);
		view.setSexo((String)values[4]);
		view.setTipoPessoa((String)values[5]);
		view.setAtivo((String)values[6]);
		view.setRgNum((String)values[7]);
		view.setRgDig((String)values[8]);
		
		return view;
	}
}
