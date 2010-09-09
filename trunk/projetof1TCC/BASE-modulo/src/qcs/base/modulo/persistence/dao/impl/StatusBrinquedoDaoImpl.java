package qcs.base.modulo.persistence.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

import qcs.base.modulo.persistence.dao.StatusBrinquedoDao;
import qcs.base.negocio.StatusBrinquedo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.StatusBrinquedoTransformer;
import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class StatusBrinquedoDaoImpl extends HibernateDaoImpl<StatusBrinquedo, Long>
implements StatusBrinquedoDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(StatusBrinquedoDaoImpl.class);

	public StatusBrinquedoDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<StatusBrinquedo> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusBrinquedo"));
			pList.add(Projections.property("descricao"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra statusBrinquedo pelo descrição
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
				}

			}

			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			return (Collection<StatusBrinquedo>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<StatusBrinquedoView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusBrinquedo"));
			pList.add(Projections.property("descricao"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra statusBrinquedo pelo descrição
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
				}

			}

			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			c.setResultTransformer(new StatusBrinquedoTransformer());
			return (Collection<StatusBrinquedoView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public StatusBrinquedo update(StatusBrinquedo objeto) throws InfrastructureException, Exception {
		try{
			return (StatusBrinquedo) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception {
		try{	
			Criteria c = getSession().createCriteria(classeEntidade);

			if(atributosFiltros != null){

				//filtra statusBrinquedo pelo descriçãoa
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
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
	public Map<Long, String> listOfValues() throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValues = new HashMap<Long, String>();

			Criteria c = getSession().createCriteria(StatusBrinquedo.class);		
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusBrinquedo"));
			pList.add(Projections.property("descricao"));
			c.setProjection(pList);

			c.addOrder(Order.asc("descricao"));

			List<Object[]> objetos = c.list();

			for(Object[] objeto : objetos){
				listOfValues.put((Long)objeto[0], (String)objeto[1]);
			}
			return listOfValues;
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

}
