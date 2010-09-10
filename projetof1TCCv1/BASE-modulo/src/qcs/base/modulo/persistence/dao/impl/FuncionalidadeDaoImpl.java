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

import qcs.base.modulo.Funcionalidade;
import qcs.base.modulo.persistence.dao.FuncionalidadeDao;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.transformer.FuncionalidadeTransformer;
import qcs.persistence.rhdefensoria.view.FuncionalidadeView;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class FuncionalidadeDaoImpl extends HibernateDaoImpl<Funcionalidade, Long>
implements FuncionalidadeDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(FuncionalidadeDaoImpl.class);

	public FuncionalidadeDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Funcionalidade> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("moduloSistema", "modSist", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){				

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nomeFuncionalidade, MatchMode.ANYWHERE));
				}

				String visivel = (String)atributosFiltros.get("visivel");
				log.debug("\tvisivel listWithFilterToView: "+visivel);
				if(visivel != null && !visivel.trim().isEmpty()){
					c.add(Restrictions.eq("visivelMenu", visivel));
				}

				Long idModulo = (Long)atributosFiltros.get("idMod");
				log.debug("\t idModulo listWithFilterToView: "+idModulo);
				if(idModulo != null && idModulo != 0){
					c.add(Restrictions.eq("modSist.idModulo", idModulo));
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

			return (Collection<Funcionalidade>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<FuncionalidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("moduloSistema", "modSist", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idFunc"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("descricao"));
			pList.add(Projections.property("visivelMenu"));
			pList.add(Projections.property("modSist.nome"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade listWithFilterToView: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nomeFuncionalidade, MatchMode.ANYWHERE));
				}

				String visivel = (String)atributosFiltros.get("visivel");
				log.debug("\tvisivel listWithFilterToView: "+visivel);
				if(visivel != null && !visivel.trim().isEmpty()){
					c.add(Restrictions.eq("visivelMenu", visivel));
				}

				Long idModulo = (Long)atributosFiltros.get("idMod");
				log.debug("\t idModulo listWithFilterToView: "+idModulo);
				if(idModulo != null && idModulo != 0){
					c.add(Restrictions.eq("modSist.idModulo", idModulo));
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

			c.setResultTransformer(new FuncionalidadeTransformer());
			return (Collection<FuncionalidadeView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public Funcionalidade update(Funcionalidade objeto) throws InfrastructureException, Exception {
		try{
			return (Funcionalidade) getSession().merge(objeto);
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
			c.createAlias("moduloSistema", "modSist", Criteria.LEFT_JOIN);			

			if(atributosFiltros != null){

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade getMaxRows: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("nome", nomeFuncionalidade, MatchMode.ANYWHERE));
				}

				String visivel = (String)atributosFiltros.get("visivel");
				log.debug("\tvisivel listWithFilterToView: "+visivel);
				if(visivel != null && !visivel.trim().isEmpty()){
					c.add(Restrictions.eq("visivelMenu", visivel));
				}

				Long idModulo = (Long)atributosFiltros.get("idMod");
				log.debug("\t idModulo listWithFilterToView: "+idModulo);
				if(idModulo != null && idModulo != 0){
					c.add(Restrictions.eq("modSist.idModulo", idModulo));
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

			Criteria c = getSession().createCriteria(Funcionalidade.class);		
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idFunc"));
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
	
}
