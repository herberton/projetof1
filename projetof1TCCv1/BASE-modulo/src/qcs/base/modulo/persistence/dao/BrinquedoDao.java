package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.negocio.Brinquedo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.BrinquedoView;
import qcs.persistence.rhdefensoria.view.LovBrinquedoView;
import qcs.persistence.template.hibernate.HibernateDao;


public interface BrinquedoDao extends HibernateDao<Brinquedo, Long> {

	public Collection<BrinquedoView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Collection<LovBrinquedoView> listOfValuesWithFilter(
			Map<String, Object> atributosFiltros, int first, int max, String orderField,
			boolean descending)throws InfrastructureException, Exception;
}
