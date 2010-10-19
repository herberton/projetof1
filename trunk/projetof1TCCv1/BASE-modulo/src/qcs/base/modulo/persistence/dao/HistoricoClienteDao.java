package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.negocio.Cliente;
import qcs.base.negocio.HistoricoCliente;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.HistoricoClienteView;
import qcs.persistence.template.hibernate.HibernateDao;


public interface HistoricoClienteDao extends HibernateDao<HistoricoCliente, Long> {

	public Collection<HistoricoClienteView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public HistoricoCliente retornaHistoricoCliente(Long idCliente);	
}
