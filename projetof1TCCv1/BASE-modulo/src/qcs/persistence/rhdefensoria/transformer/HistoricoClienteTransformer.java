package qcs.persistence.rhdefensoria.transformer;

import java.util.Date;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import qcs.persistence.rhdefensoria.view.HistoricoClienteView;

public class HistoricoClienteTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public Object transformTuple(Object[] values, String[] aliases) {
		HistoricoClienteView view = new HistoricoClienteView();
		view.setId_historico_cliente((Long) values[0]);
		view.setData_hora_entrada_parque((Date) values[1]);
		view.setData_hora_saida_parque((Date) values[2]);
		view.setObservacao((String) values[3]);
		view.setIdDispositivo((Long) values[4]);
		view.setIdStatusDispositivo((Long) values[5]);
		view.setIdCliente((Long) values[6]);		
		view.setNomeCliente((String) values[7]);

		return view;
	}
}
