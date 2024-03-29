package qcs.base.modulo.persistence.dao.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import qcs.base.modulo.persistence.dao.DispositivoDao;
import qcs.base.negocio.Dispositivo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.DispositivoTransformer;
import qcs.persistence.rhdefensoria.view.DispositivoView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class DispositivoDaoImpl extends HibernateDaoImpl<Dispositivo, Long> implements DispositivoDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(FuncionalidadeDaoImpl.class);

	public DispositivoDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Dispositivo> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception {
		try {

			log.debug("Listar "+classeEntidade+" de "+first+" at� "+max+".");

			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("statusDispositivo", "sd", Criteria.LEFT_JOIN);			
			c.createAlias("cliente", "cli", Criteria.LEFT_JOIN);			

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idDispositivo"));
			pList.add(Projections.property("ip"));
			pList.add(Projections.property("sd.descricao"));

			c.setProjection(pList);


			if(atributosFiltros != null){


				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}

				//STATUS DISPOSITIVO...
				Long idStatusDispositivo = (Long)atributosFiltros.get("idStatusDispositivo");
				log.debug("\tidStatusDispositivo listWithFilterToView: " + idStatusDispositivo);
				if(idStatusDispositivo != null && idStatusDispositivo != 0){
					c.add(Restrictions.eq("sd.idStatusDispositivo", idStatusDispositivo));
				}	

				//Dispositivos Utilizados
				String utilizados = (String)atributosFiltros.get("utilizados");
				log.debug("\tip listWithFilterToView: " + ip);
				if(utilizados != null && !utilizados.trim().isEmpty()){
					if("sim".equals(utilizados))
						c.add(Restrictions.isNotNull("cli.idCliente"));
					else
						c.add(Restrictions.isNull("cli.idCliente"));
				}				

			}

			c.setFirstResult(first);
			c.setMaxResults(max);

			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			return (Collection<Dispositivo>)c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Dispositivo update(Dispositivo objeto)
	throws InfrastructureException, Exception {
		try{
			return (Dispositivo) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)
	throws InfrastructureException, Exception {

		try {
			Criteria c = getSession().createCriteria(classeEntidade);			
			c.createAlias("statusDispositivo", "sd", Criteria.LEFT_JOIN);
			c.createAlias("cliente", "cli", Criteria.LEFT_JOIN);				

			if(atributosFiltros != null){


				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}

				//STATUS DISPOSITIVO...
				Long idStatusDispositivo = (Long)atributosFiltros.get("idStatusDispositivo");
				log.debug("\tidStatusDispositivo listWithFilterToView: " + idStatusDispositivo);
				if(idStatusDispositivo != null && idStatusDispositivo != 0){
					c.add(Restrictions.eq("sd.idStatusDispositivo", idStatusDispositivo));
				}		

				//Dispositivos Utilizados
				String utilizados = (String)atributosFiltros.get("utilizados");
				log.debug("\tip listWithFilterToView: " + ip);
				if(utilizados != null && !utilizados.trim().isEmpty()){
					if("sim".equals(utilizados))
						c.add(Restrictions.isNotNull("cli.idCliente"));
					else
						c.add(Restrictions.isNull("cli.idCliente"));
				}					

			}

			c.setProjection(Projections.rowCount());

			return (Integer)c.uniqueResult();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DispositivoView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception {

		try {

			log.debug("Listar "+classeEntidade+" de "+first+" at� "+max+".");

			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("statusDispositivo", "sd", Criteria.LEFT_JOIN);
			c.createAlias("cliente", "cli", Criteria.LEFT_JOIN);				

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idDispositivo"));
			pList.add(Projections.property("ip"));
			pList.add(Projections.property("sd.descricao"));

			c.setProjection(pList);


			if(atributosFiltros != null){


				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}

				//STATUS DISPOSITIVO...
				Long idStatusDispositivo = (Long)atributosFiltros.get("idStatusDispositivo");
				log.debug("\tidStatusDispositivo listWithFilterToView: " + idStatusDispositivo);
				if(idStatusDispositivo != null && idStatusDispositivo != 0){
					c.add(Restrictions.eq("sd.idStatusDispositivo", idStatusDispositivo));
				}

				//Dispositivos Utilizados
				String utilizados = (String)atributosFiltros.get("utilizados");
				log.debug("\tip listWithFilterToView: " + ip);
				if(utilizados != null && !utilizados.trim().isEmpty()){
					if("sim".equals(utilizados))
						c.add(Restrictions.isNotNull("cli.idCliente"));
					else
						c.add(Restrictions.isNull("cli.idCliente"));
				}				

			}

			c.setFirstResult(first);
			c.setMaxResults(max);

			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			c.setResultTransformer(new DispositivoTransformer());

			return (Collection<DispositivoView>)c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	public Dispositivo validaDispositivo(String cod_dispositivo){

		try{		
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("cliente", "cli", Criteria.LEFT_JOIN);
			c.add(Restrictions.eq("idRfid", cod_dispositivo));
			c.add(Restrictions.isNull("cli.idCliente"));
			return (Dispositivo)c.uniqueResult();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}		
	}
}
