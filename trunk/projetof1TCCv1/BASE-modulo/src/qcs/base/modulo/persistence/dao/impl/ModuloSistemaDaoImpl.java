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

import qcs.base.modulo.ModuloSistema;
import qcs.base.modulo.persistence.dao.ModuloSistemaDao;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.ListOfValuesModuloSistemaTransformer;
import qcs.persistence.rhdefensoria.transformer.ModuloSistemaTransformer;
import qcs.persistence.rhdefensoria.view.ListOfValuesModuloSistemaView;
import qcs.persistence.rhdefensoria.view.ModuloSistemaView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class ModuloSistemaDaoImpl extends HibernateDaoImpl<ModuloSistema, Long>
implements ModuloSistemaDao {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(ModuloSistemaDaoImpl.class);

	public ModuloSistemaDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ModuloSistema> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			if(atributosFiltros != null){	

				//filtra módulos por nome do módulo
				String nomeModulo = (String)atributosFiltros.get("nomeModulo");
				log.debug("\tnomeModulo: "+ nomeModulo);
				if(nomeModulo != null && !nomeModulo.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nomeModulo,MatchMode.ANYWHERE));
			}			

			return (Collection<ModuloSistema>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ModuloSistemaView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();			
			pList.add(Projections.property("idModulo"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("posicao"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("principal"));

			c.setProjection(pList);


			if(atributosFiltros != null){	

				//filtra módulos por nome do módulo
				String nomeModulo = (String)atributosFiltros.get("nomeModulo");
				log.debug("\tnomeModulo: "+ nomeModulo);
				if(nomeModulo != null && !nomeModulo.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nomeModulo,MatchMode.ANYWHERE));
			}	

			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			c.setResultTransformer(new ModuloSistemaTransformer());
			log.debug(">>> query: "+c.toString());
			return (Collection<ModuloSistemaView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public ModuloSistema update(ModuloSistema objeto) throws InfrastructureException, Exception {
		try{
			return (ModuloSistema) getSession().merge(objeto);
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

				//filtra módulos por nome do módulo
				String nomeModulo = (String)atributosFiltros.get("nomeModulo");
				log.debug("\tnomeModulo: "+ nomeModulo);
				if(nomeModulo != null && !nomeModulo.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nomeModulo,MatchMode.ANYWHERE));

				//filtra módulos por módulos principais				
//				String tipoModulo = (String)atributosFiltros.get("tipoModulo");
//				log.debug("\ttipoModulo: "+ tipoModulo);		
//
//
//				if (tipoModulo != null && "P".equals(tipoModulo)) { // módulos principais
//					c.add(Restrictions.eq("principal", "S"));
//				} else if (tipoModulo != null && "F".equals(tipoModulo)) { //módulos filhos - F
//					c.add(Restrictions.isNotNull("moduloSistema"));
//				}

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
			Criteria c = getSession().createCriteria(ModuloSistema.class);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idModulo"));
			pList.add(Projections.property("nome"));

			c.setProjection(pList);
			c.addOrder(Order.asc("nome"));

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


	@SuppressWarnings("unchecked")
	@Override
	public Collection<ListOfValuesModuloSistemaView> listOfValuesWithFilter(
			Map<String, Object> atributosFiltros, int first, int max, String orderField,
			boolean descending) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idModulo"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("descricao"));

			c.setProjection(pList);

			if(atributosFiltros!=null){

				String nome = (String)atributosFiltros.get("nomeModulo");
				log.debug("listOfValuesWithFilter : nomeModulo: "+nome);
				if(nome != null && !nome.trim().isEmpty()){
					c.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
				}
			}


			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			c.setResultTransformer(new ListOfValuesModuloSistemaTransformer());
			return (Collection<ListOfValuesModuloSistemaView>)c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}



}
