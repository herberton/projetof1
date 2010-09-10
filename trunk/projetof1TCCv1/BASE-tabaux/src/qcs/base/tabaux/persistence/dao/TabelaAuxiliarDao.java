package qcs.base.tabaux.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface TabelaAuxiliarDao extends HibernateDao<TabelaAuxiliar, Long> {

	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Collection<TabelaAuxiliarView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception;
}
