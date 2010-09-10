package qcs.base.pessoa.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.persistence.view.LovPessoaView;
import qcs.base.pessoa.persistence.view.PessoaView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;



public interface PessoaDao extends HibernateDao<Pessoa, Long> {
	
	public Collection<PessoaView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max, String orderField, boolean descending) throws InfrastructureException, Exception;
	public Collection<LovPessoaView> listOfValuesWithFilter(Map<String, Object> atributosFiltros, int first, int max, String orderField, boolean descending) throws InfrastructureException, Exception;
	int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception;
	
}
