package qcs.base.seguranca.persistence.dao.impl;

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

import qcs.base.seguranca.PerfilFuncionalidade;
import qcs.base.seguranca.persistence.dao.PerfilFuncionalidadeDao;
import qcs.base.seguranca.persistence.transformer.PerfilFuncionalidadeTransformer;
import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class PerfilFuncionalidadeDaoImpl extends HibernateDaoImpl<PerfilFuncionalidade, Long>
implements PerfilFuncionalidadeDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(PerfilFuncionalidadeDaoImpl.class);

	public PerfilFuncionalidadeDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PerfilFuncionalidade> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("perfil", "perfil", Criteria.LEFT_JOIN);
			c.createAlias("funcionalidade", "funcionalidade", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){				
				//filtra funcionalidades por perfil
				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("\tidPerfil: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){					
					c.add(Restrictions.eq("perfil.idPerfil", idPerfil));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomePerfil = (String)atributosFiltros.get("nomePerfil");
				log.debug("\tnomePerfil: "+nomePerfil);
				if(nomePerfil != null && !nomePerfil.trim().isEmpty()){				
					c.add(Restrictions.ilike("perfil.nomePerfil", nomePerfil, MatchMode.ANYWHERE));
				}
				
				//filtra funcionalidades por perfil
				Long idFunc = (Long)atributosFiltros.get("idFunc");
				log.debug("\tidFunc: "+idFunc);
				if(idFunc != null && idFunc != 0){					
					c.add(Restrictions.eq("funcionalidade.idFunc", idFunc));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("funcionalidade.nome", nomeFuncionalidade, MatchMode.ANYWHERE));
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

			return (Collection<PerfilFuncionalidade>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PerfilFuncionalidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("perfil", "perfil", Criteria.LEFT_JOIN);
			c.createAlias("funcionalidade", "funcionalidade", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPerfilFunc"));
			pList.add(Projections.property("perfil.idPerfil"));
			pList.add(Projections.property("perfil.nomePerfil"));
			pList.add(Projections.property("funcionalidade.idFunc"));
			pList.add(Projections.property("funcionalidade.nome"));
			pList.add(Projections.property("ativo"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra funcionalidades por perfil
				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("\tidPerfil: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){					
					c.add(Restrictions.eq("perfil.idPerfil", idPerfil));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomePerfil = (String)atributosFiltros.get("nomePerfil");
				log.debug("\tnomePerfil: "+nomePerfil);
				if(nomePerfil != null && !nomePerfil.trim().isEmpty()){				
					c.add(Restrictions.ilike("perfil.nomePerfil", nomePerfil, MatchMode.ANYWHERE));
				}
				
				//filtra funcionalidades por perfil
				Long idFunc = (Long)atributosFiltros.get("idFunc");
				log.debug("\tidFunc: "+idFunc);
				if(idFunc != null && idFunc != 0){					
					c.add(Restrictions.eq("funcionalidade.idFunc", idFunc));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("funcionalidade.nome", nomeFuncionalidade, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new PerfilFuncionalidadeTransformer());
			return (Collection<PerfilFuncionalidadeView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public PerfilFuncionalidade update(PerfilFuncionalidade objeto) throws InfrastructureException, Exception {
		try{
			return (PerfilFuncionalidade) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception {
		try{	
			Criteria c = getSession().createCriteria(PerfilFuncionalidade.class);
			c.createAlias("perfil", "perfil", Criteria.LEFT_JOIN);
			c.createAlias("funcionalidade", "funcionalidade", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){

				//filtra funcionalidades por perfil
				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("\tidPerfil: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){					
					c.add(Restrictions.eq("perfil.idPerfil", idPerfil));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomePerfil = (String)atributosFiltros.get("nomePerfil");
				log.debug("\tnomePerfil: "+nomePerfil);
				if(nomePerfil != null && !nomePerfil.trim().isEmpty()){				
					c.add(Restrictions.ilike("perfil.nomePerfil", nomePerfil, MatchMode.ANYWHERE));
				}
				
				//filtra funcionalidades por perfil
				Long idFunc = (Long)atributosFiltros.get("idFunc");
				log.debug("\tidFunc: "+idFunc);
				if(idFunc != null && idFunc != 0){					
					c.add(Restrictions.eq("funcionalidade.idFunc", idFunc));
				}

				//filtra funcionalidades pelo nome da funcionalidade
				String nomeFuncionalidade = (String)atributosFiltros.get("nomeFuncionalidade");
				log.debug("\tnomeFuncionalidade: "+nomeFuncionalidade);
				if(nomeFuncionalidade != null && !nomeFuncionalidade.trim().isEmpty()){				
					c.add(Restrictions.ilike("funcionalidade.nome", nomeFuncionalidade, MatchMode.ANYWHERE));
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
