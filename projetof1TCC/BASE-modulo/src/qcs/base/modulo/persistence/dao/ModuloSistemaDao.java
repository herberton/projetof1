package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.modulo.ModuloSistema;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.ListOfValuesModuloSistemaView;
import qcs.persistence.rhdefensoria.view.ModuloSistemaView;
import qcs.persistence.template.hibernate.HibernateDao;

public interface ModuloSistemaDao extends HibernateDao<ModuloSistema, Long> {	
	public Collection<ModuloSistemaView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Map<Long, String> listOfValues()throws InfrastructureException, Exception;
	public Collection<ListOfValuesModuloSistemaView> listOfValuesWithFilter(
			Map<String, Object> atributosFiltros, int first, int max, String orderField,
			boolean descending)throws InfrastructureException, Exception;
	
}
