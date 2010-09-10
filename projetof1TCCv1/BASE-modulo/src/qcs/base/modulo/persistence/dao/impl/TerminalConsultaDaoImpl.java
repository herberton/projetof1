package qcs.base.modulo.persistence.dao.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import qcs.base.modulo.persistence.dao.TerminalConsultaDao;
import qcs.base.negocio.TerminalConsulta;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.TerminalConsultaTransformer;
import qcs.persistence.rhdefensoria.view.TerminalConsultaView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class TerminalConsultaDaoImpl extends HibernateDaoImpl<TerminalConsulta, Long> implements TerminalConsultaDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(FuncionalidadeDaoImpl.class);
	
	public TerminalConsultaDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TerminalConsulta> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception {
		try {
			
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			
			Criteria c = getSession().createCriteria(classeEntidade);
			
			ProjectionList pList = Projections.projectionList();
			
			pList.add(Projections.property("idTerminalConsulta"));
			pList.add(Projections.property("ip"));
			pList.add(Projections.property("hostName"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("localizacao"));
			
			
			
			c.setProjection(pList);
			
			
			if(atributosFiltros != null){
				
				
				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}
				
				//HOSTNAME...
				String hostName = (String)atributosFiltros.get("hostName");
				log.debug("\thostName listWithFilterToView: " + hostName);
				if(hostName != null && !hostName.trim().isEmpty()){
					c.add(Restrictions.ilike("hostName", hostName, MatchMode.ANYWHERE));
				}
				
				//LOCALIZACAO...
				String localizacao =(String)atributosFiltros.get("localizacao");
				log.debug("\tlocalizacao listWithFilterToView: " + localizacao);
				if(localizacao != null && !localizacao.trim().isEmpty()){
					c.add(Restrictions.ilike("localizacao", localizacao, MatchMode.ANYWHERE));
				}		
							
			}
			
			c.setFirstResult(first);
			c.setMaxResults(max);

			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			
			return (Collection<TerminalConsulta>)c.list();
			
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public TerminalConsulta update(TerminalConsulta objeto)
			throws InfrastructureException, Exception {
		try{
			return (TerminalConsulta) getSession().merge(objeto);
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
			
			if(atributosFiltros != null){
				
				
				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}
				
				//HOSTNAME...
				String hostName = (String)atributosFiltros.get("hostName");
				log.debug("\thostName listWithFilterToView: " + hostName);
				if(hostName != null && !hostName.trim().isEmpty()){
					c.add(Restrictions.ilike("hostName", hostName, MatchMode.ANYWHERE));
				}
				
				//LOCALIZACAO...
				String localizacao =(String)atributosFiltros.get("localizacao");
				log.debug("\tlocalizacao listWithFilterToView: " + localizacao);
				if(localizacao != null && !localizacao.trim().isEmpty()){
					c.add(Restrictions.ilike("localizacao", localizacao, MatchMode.ANYWHERE));
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
	public Collection<TerminalConsultaView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception {
		
		try {
			
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			
			Criteria c = getSession().createCriteria(classeEntidade);
			
			
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idTerminalConsulta"));
			pList.add(Projections.property("ip"));
			pList.add(Projections.property("hostName"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("localizacao"));
			
			
			
			c.setProjection(pList);
			
			
			if(atributosFiltros != null){
				
				
				//IP...
				String ip = (String)atributosFiltros.get("ip");
				log.debug("\tip listWithFilterToView: " + ip);
				if(ip != null && !ip.trim().isEmpty()){
					c.add(Restrictions.eq("ip", ip));
				}
				
				//HOSTNAME...
				String hostName = (String)atributosFiltros.get("hostName");
				log.debug("\thostName listWithFilterToView: " + hostName);
				if(hostName != null && !hostName.trim().isEmpty()){
					c.add(Restrictions.ilike("hostName", hostName, MatchMode.ANYWHERE));
				}
				
				//LOCALIZACAO...
				String localizacao =(String)atributosFiltros.get("localizacao");
				log.debug("\tlocalizacao listWithFilterToView: " + localizacao);
				if(localizacao != null && !localizacao.trim().isEmpty()){
					c.add(Restrictions.ilike("localizacao", localizacao, MatchMode.ANYWHERE));
				}		
							
			}
			
			c.setFirstResult(first);
			c.setMaxResults(max);

			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			
			c.setResultTransformer(new TerminalConsultaTransformer());
			
			return (Collection<TerminalConsultaView>)c.list();
			
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
	
}
