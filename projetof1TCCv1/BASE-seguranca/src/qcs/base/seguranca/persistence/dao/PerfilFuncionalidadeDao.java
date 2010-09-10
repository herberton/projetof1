package qcs.base.seguranca.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.seguranca.PerfilFuncionalidade;
import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface PerfilFuncionalidadeDao extends HibernateDao<PerfilFuncionalidade, Long> {

	public Collection<PerfilFuncionalidadeView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;	

}
