package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.modulo.Funcionalidade;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.FuncionalidadeView;
import qcs.persistence.template.hibernate.HibernateDao;

public interface FuncionalidadeDao extends HibernateDao<Funcionalidade, Long> {

	public Collection<FuncionalidadeView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Map<Long, String> listOfValues()throws InfrastructureException, Exception;
}
