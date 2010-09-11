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
import qcs.base.modulo.persistence.dao.CatracaDao;
import qcs.base.negocio.Catraca;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.CatracaTransformer;
import qcs.persistence.rhdefensoria.view.CatracaView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class CatracaDaoImpl extends HibernateDaoImpl<Catraca, Long>
implements CatracaDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(CatracaDaoImpl.class);

	public CatracaDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Catraca> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("brinquedo", "brinq", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCatraca"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("localizacao"));
			pList.add(Projections.property("brinq.nome"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra catracas pelo nome da catraca
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
				}

				//filtra catracas pelo nome do brinquedo
				String nomeBrinquedo = (String)atributosFiltros.get("nomeBrinquedo");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(nomeBrinquedo != null && !nomeBrinquedo.trim().isEmpty()){				
					c.add(Restrictions.ilike("brinq.nome", nomeBrinquedo, MatchMode.ANYWHERE));
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

			return (Collection<Catraca>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CatracaView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("brinquedo", "brinq", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCatraca"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("localizacao"));
			pList.add(Projections.property("brinq.nome"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra catracas pelo nome da catraca
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
				}

				//filtra catracas pelo nome do brinquedo
				String nomeBrinquedo = (String)atributosFiltros.get("nomeBrinquedo");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(nomeBrinquedo != null && !nomeBrinquedo.trim().isEmpty()){				
					c.add(Restrictions.ilike("brinq.nome", nomeBrinquedo, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new CatracaTransformer());
			return (Collection<CatracaView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public Catraca update(Catraca objeto) throws InfrastructureException, Exception {
		try{
			return (Catraca) getSession().merge(objeto);
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
			c.createAlias("brinquedo", "brinq", Criteria.LEFT_JOIN);			

			if(atributosFiltros != null){

				//filtra catracas pelo nome da catraca
				String descricao = (String)atributosFiltros.get("descricao");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(descricao != null && !descricao.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE));
				}
				
				//filtra catracas pelo nome do brinquedo
				String nomeBrinquedo = (String)atributosFiltros.get("nomeBrinquedo");
				log.debug("\tdescricao listWithFilterToView: "+descricao);
				if(nomeBrinquedo != null && !nomeBrinquedo.trim().isEmpty()){				
					c.add(Restrictions.ilike("brinq.nome", nomeBrinquedo, MatchMode.ANYWHERE));
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

}
