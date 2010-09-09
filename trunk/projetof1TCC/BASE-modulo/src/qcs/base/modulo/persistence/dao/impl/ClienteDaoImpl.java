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

import qcs.base.modulo.persistence.dao.ClienteDao;
import qcs.base.negocio.Cliente;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.ClienteTransformer;
import qcs.persistence.rhdefensoria.view.ClienteView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class ClienteDaoImpl extends HibernateDaoImpl<Cliente, Long>
implements ClienteDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(ClienteDaoImpl.class);

	public ClienteDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Cliente> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCliente"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("dataNascimento"));
			pList.add(Projections.property("qtdVisitas"));
			pList.add(Projections.property("rg"));
			pList.add(Projections.property("cpf"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra clientes pelo nome do clientes
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
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

			return (Collection<Cliente>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ClienteView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			
			Criteria c = getSession().createCriteria(classeEntidade);
			
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCliente"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("dataNascimento"));
			pList.add(Projections.property("qtdVisitas"));
			pList.add(Projections.property("rg"));
			pList.add(Projections.property("cpf"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra clientes pelo nome do clientes
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new ClienteTransformer());
			return (Collection<ClienteView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public Cliente update(Cliente objeto) throws InfrastructureException, Exception {
		try{
			return (Cliente) getSession().merge(objeto);
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

				//filtra clientes pelo nome do clientes
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome listWithFilterToView: "+nome);
				if(nome != null && !nome.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
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