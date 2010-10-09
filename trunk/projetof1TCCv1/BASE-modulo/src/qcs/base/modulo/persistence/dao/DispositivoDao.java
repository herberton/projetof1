package qcs.base.modulo.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.negocio.Dispositivo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.DispositivoView;
import qcs.persistence.template.hibernate.HibernateDao;

public interface DispositivoDao extends HibernateDao<Dispositivo, Long> {

	public Collection<DispositivoView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception ;
	
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception;
	//public Map<Long, String> listOfValues()throws InfrastructureException, Exception;

	
	public Dispositivo validaDispositivo(String cod_dispositivo);
}
