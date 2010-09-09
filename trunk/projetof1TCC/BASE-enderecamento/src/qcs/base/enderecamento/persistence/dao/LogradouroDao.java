package qcs.base.enderecamento.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.enderecamento.Logradouro;
import qcs.base.enderecamento.persistence.view.LogradouroView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface LogradouroDao extends HibernateDao<Logradouro, Long> {
	public Collection<LogradouroView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,String orderField, boolean descending)  throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception;
}
