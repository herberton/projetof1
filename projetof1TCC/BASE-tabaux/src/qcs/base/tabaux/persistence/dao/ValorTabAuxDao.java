package qcs.base.tabaux.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.view.ValorTabAuxView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface ValorTabAuxDao extends HibernateDao<ValorTabAux, Long> {

	public Collection<ValorTabAuxView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception;
	
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;

	//lista de UF
	public Map<Long, String> listOfValuesUf()throws InfrastructureException, Exception;
	//lista de Tipo de Logradouro
	public Map<Long, String> listOfValuesTipoLog() throws InfrastructureException,
			Exception;
	//lista de Tipo de Pessoa
	public Map<Long, String> listOfValuesTipoPessoa() throws InfrastructureException,
			Exception;

}
