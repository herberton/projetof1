package qcs.base.seguranca.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.seguranca.PerfilUsuario;
import qcs.base.seguranca.persistence.view.PerfilUsuarioView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface PerfilUsuarioDao extends HibernateDao<PerfilUsuario, Long> {
	
	public Collection<PerfilUsuarioView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception;
	
}
