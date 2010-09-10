package qcs.base.seguranca.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.HibernateDao;



public interface PerfilDao extends HibernateDao<Perfil, Long> {
	
	public Collection<PerfilView> listWithFilterToView(Map<String, Object> atributosFiltros, int first, int max,	String orderField, boolean descending)throws InfrastructureException, Exception;
	public Map<Long, String> listOfValues()throws InfrastructureException, Exception;
	
}
