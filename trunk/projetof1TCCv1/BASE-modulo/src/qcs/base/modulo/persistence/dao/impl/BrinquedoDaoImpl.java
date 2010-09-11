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

import qcs.base.modulo.persistence.dao.BrinquedoDao;
import qcs.base.negocio.Brinquedo;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.BrinquedoTransformer;
import qcs.persistence.rhdefensoria.transformer.LovBrinquedoTransformer;
import qcs.persistence.rhdefensoria.view.BrinquedoView;
import qcs.persistence.rhdefensoria.view.LovBrinquedoView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class BrinquedoDaoImpl extends HibernateDaoImpl<Brinquedo, Long>
implements BrinquedoDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(BrinquedoDaoImpl.class);

	public BrinquedoDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Brinquedo> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("statusBrinquedo", "statusBrinq", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBrinquedo"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("tempoMensagem"));
			pList.add(Projections.property("estimativaTempoFila"));
			pList.add(Projections.property("qtdPessoasFilaFisica"));
			pList.add(Projections.property("qtdMaxPessoasFilaFisica"));
			pList.add(Projections.property("statusBrinq.descricao"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra brinquedos pelo nome do brinquedos
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}

				//filtra brinquedos pelo status do brinquedos
				Long status = (Long)atributosFiltros.get("idStatusBrinquedo");
				log.debug("\tStatus do Brinquedo listWithFilterToView: "+status);
				if(status != null){				
					c.add(Restrictions.eq("statusBrinq.idStatusBrinquedo", status));
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

			return (Collection<Brinquedo>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BrinquedoView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("statusBrinquedo", "statusBrinq", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBrinquedo"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("tempoMensagem"));
			pList.add(Projections.property("estimativaTempoFila"));
			pList.add(Projections.property("qtdPessoasFilaFisica"));
			pList.add(Projections.property("qtdMaxPessoasFilaFisica"));
			pList.add(Projections.property("statusBrinq.idStatusBrinquedo"));
			pList.add(Projections.property("statusBrinq.descricao"));			
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra brinquedos pelo nome do brinquedos
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}

				//filtra brinquedos pelo status do brinquedos
				Long status = (Long)atributosFiltros.get("idStatusBrinquedo");
				log.debug("\tStatus do Brinquedo listWithFilterToView: "+status);
				if(status != null && status != 0){				
					c.add(Restrictions.eq("statusBrinq.idStatusBrinquedo", status));
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

			c.setResultTransformer(new BrinquedoTransformer());
			return (Collection<BrinquedoView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public Brinquedo update(Brinquedo objeto) throws InfrastructureException, Exception {
		try{
			return (Brinquedo) getSession().merge(objeto);
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

				//filtra brinquedos pelo nome do brinquedos
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}

				//filtra brinquedos pelo status do brinquedos
				Long status = (Long)atributosFiltros.get("idStatusBrinquedo");
				log.debug("\tStatus do Brinquedo listWithFilterToView: "+status);
				if(status != null){				
					c.add(Restrictions.eq("statusBrinq.idStatusBrinquedo", status));
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
	public Collection<LovBrinquedoView> listOfValuesWithFilter(
			Map<String, Object> atributosFiltros, int first, int max, String orderField,
			boolean descending)throws InfrastructureException, Exception{
		log.debug("BrinquedoDaoImpl : listOfValuesWihtFilter\n\tparâmetros:");
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("statusBrinquedo", "statusBrinq", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBrinquedo"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("statusBrinq.descricao"));

			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra o nome do Brinquedo colocando like
				String nomeBrinquedo = (String)atributosFiltros.get("nomeBrinquedo");
				log.debug("Nome Brinquedo listOfValuesWihtFilter: "+nomeBrinquedo);
				if(nomeBrinquedo != null && !nomeBrinquedo.trim().isEmpty()){
					c.add(Restrictions.ilike("nome", nomeBrinquedo, MatchMode.ANYWHERE));
				}

			}
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);

			c.setResultTransformer(new LovBrinquedoTransformer());
			return (Collection<LovBrinquedoView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}		
}