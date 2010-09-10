package qcs.base.enderecamento.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface CidadeDao extends HibernateDao<Cidade, Long> {
	
	int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception;

	public Collection<CidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception;

	public Map<Long, String> listOfValues() throws InfrastructureException, Exception;

	public Map<Long, String> listOfValuesByUf(Long codUf)
			throws InfrastructureException, Exception;

	public Collection<CidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros)
			throws InfrastructureException, Exception;
	
	
}
