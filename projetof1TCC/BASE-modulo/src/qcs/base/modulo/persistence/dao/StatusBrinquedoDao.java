package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.negocio.StatusBrinquedo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;
import qcs.persistence.template.hibernate.HibernateDao;


public interface StatusBrinquedoDao extends HibernateDao<StatusBrinquedo, Long> {

	public Collection<StatusBrinquedoView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Map<Long, String> listOfValues()throws InfrastructureException, Exception;
}
