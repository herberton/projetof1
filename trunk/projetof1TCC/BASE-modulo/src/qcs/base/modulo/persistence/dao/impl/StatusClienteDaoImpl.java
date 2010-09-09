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

import qcs.base.modulo.persistence.dao.StatusClienteDao;
import qcs.base.negocio.StatusCliente;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.StatusClienteTransformer;
import qcs.persistence.rhdefensoria.view.StatusClienteView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class StatusClienteDaoImpl extends HibernateDaoImpl<StatusCliente, Long>
implements StatusClienteDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(StatusClienteDaoImpl.class);

	public StatusClienteDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<StatusCliente> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusCliente"));
			pList.add(Projections.property("descricao"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra statusCliente pelo descrição
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

			return (Collection<StatusCliente>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<StatusClienteView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusCliente"));
			pList.add(Projections.property("descricao"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra statusCliente pelo descrição
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

			c.setResultTransformer(new StatusClienteTransformer());
			return (Collection<StatusClienteView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public StatusCliente update(StatusCliente objeto) throws InfrastructureException, Exception {
		try{
			return (StatusCliente) getSession().merge(objeto);
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

				//filtra statusCliente pelo descrição
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

			Criteria c = getSession().createCriteria(StatusCliente.class);		
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idStatusCliente"));
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
