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

import qcs.base.modulo.persistence.dao.HistoricoClienteDao;
import qcs.base.negocio.HistoricoCliente;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.HistoricoClienteTransformer;
import qcs.persistence.rhdefensoria.view.HistoricoClienteView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class HistoricoClienteDaoImpl extends HibernateDaoImpl<HistoricoCliente, Long>
implements HistoricoClienteDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(HistoricoClienteDaoImpl.class);

	public HistoricoClienteDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HistoricoCliente> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("dispositivo", "disp", Criteria.LEFT_JOIN);	
			c.createAlias("statusDispositivo", "statusDisp", Criteria.LEFT_JOIN);			
			c.createAlias("cliente", "client", Criteria.LEFT_JOIN);			

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idHistoricoCliente"));
			pList.add(Projections.property("dataHoraEntradaParque"));
			pList.add(Projections.property("dataHoraSaidaParque"));
			pList.add(Projections.property("observacao"));
			pList.add(Projections.property("disp.idDispositivo"));
			pList.add(Projections.property("statusDisp.idStatusDispositivo"));
			pList.add(Projections.property("client.idCliente"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra brinquedos pelo nome do brinquedos
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}

				//filtra brinquedos pelo status do brinquedos
				Long status = (Long)atributosFiltros.get("idStatusHistoricoCliente");
				log.debug("\tStatus do HistoricoCliente listWithFilterToView: "+status);
				if(status != null){				
					c.add(Restrictions.eq("statusBrinq.idStatusHistoricoCliente", status));
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

			return (Collection<HistoricoCliente>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<HistoricoClienteView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("dispositivo", "disp", Criteria.LEFT_JOIN);	
			c.createAlias("statusDispositivo", "statusDisp", Criteria.LEFT_JOIN);			
			c.createAlias("cliente", "client", Criteria.LEFT_JOIN);	

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idHistoricoCliente"));
			pList.add(Projections.property("dataHoraEntradaParque"));
			pList.add(Projections.property("dataHoraSaidaParque"));
			pList.add(Projections.property("observacao"));
			pList.add(Projections.property("disp.idDispositivo"));
			pList.add(Projections.property("statusDisp.idStatusDispositivo"));
			pList.add(Projections.property("client.idCliente"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra brinquedos pelo nome do brinquedos
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}

				//filtra brinquedos pelo status do brinquedos
				Long status = (Long)atributosFiltros.get("idStatusHistoricoCliente");
				log.debug("\tStatus do HistoricoCliente listWithFilterToView: "+status);
				if(status != null && status != 0){				
					c.add(Restrictions.eq("statusBrinq.idStatusHistoricoCliente", status));
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

			c.setResultTransformer(new HistoricoClienteTransformer());
			return (Collection<HistoricoClienteView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public HistoricoCliente update(HistoricoCliente objeto) throws InfrastructureException, Exception {
		try{
			return (HistoricoCliente) getSession().merge(objeto);
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
				Long status = (Long)atributosFiltros.get("idStatusHistoricoCliente");
				log.debug("\tStatus do HistoricoCliente listWithFilterToView: "+status);
				if(status != null){				
					c.add(Restrictions.eq("statusBrinq.idStatusHistoricoCliente", status));
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