package qcs.persistence.rhdefensoria.transformer;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.TerminalConsultaView;

public class TerminalConsultaTransformer implements ResultTransformer{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliasses) {
		
		TerminalConsultaView view = new TerminalConsultaView();
	
		view.setIdTerminalConsulta((Long)values[0]);
		view.setIp((String)values[1]);
		view.setHostName((String)values[2]);
		view.setDescricao((String)values[3]);
		view.setLocalizacao((String)values[4]);
		
		return view;
		
	}

}
