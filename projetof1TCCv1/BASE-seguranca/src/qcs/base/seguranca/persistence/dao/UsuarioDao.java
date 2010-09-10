package qcs.base.seguranca.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;

public interface UsuarioDao extends HibernateDao<Usuario, Long> {

	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	public Collection<UsuarioView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception;
	public Usuario buscar(String usuario) throws InfrastructureException, Exception;
}
