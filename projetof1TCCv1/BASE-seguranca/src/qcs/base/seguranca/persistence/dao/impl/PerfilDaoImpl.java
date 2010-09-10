package qcs.base.seguranca.persistence.dao.impl;

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

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.dao.PerfilDao;
import qcs.base.seguranca.persistence.transformer.PerfilTransformer;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class PerfilDaoImpl extends HibernateDaoImpl<Perfil, Long>
implements PerfilDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(PerfilDaoImpl.class);

	public PerfilDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Perfil> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			if(atributosFiltros != null){

				//filtra os usuários por perfil de acesso
				String nomePerfilAcesso = (String)atributosFiltros.get("nomePerfilAcesso");
				log.debug("\tnomePerfilAcesso: "+nomePerfilAcesso);
				if(nomePerfilAcesso != null && !nomePerfilAcesso.trim().isEmpty()){				
					c.add(Restrictions.ilike("nomePerfil", nomePerfilAcesso, MatchMode.ANYWHERE));
				}

				//filtra os usuários por descrição de perfil
				String descPerfilAcesso = (String)atributosFiltros.get("descPerfilAcesso");
				log.debug("\tdescPerfilAcesso: "+descPerfilAcesso);
				if(descPerfilAcesso != null && !descPerfilAcesso.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricaoPerfil", descPerfilAcesso, MatchMode.ANYWHERE));
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

			return (Collection<Perfil>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PerfilView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPerfil"));
			pList.add(Projections.property("nomePerfil"));
			pList.add(Projections.property("descricaoPerfil"));
			pList.add(Projections.property("ativo"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra os usuários por perfil de acesso
				String nomePerfilAcesso = (String)atributosFiltros.get("nomePerfilAcesso");
				log.debug("\tnomePerfilAcesso: "+nomePerfilAcesso);
				if(nomePerfilAcesso != null && !nomePerfilAcesso.trim().isEmpty()){				
					c.add(Restrictions.ilike("nomePerfil", nomePerfilAcesso, MatchMode.ANYWHERE));
				}

				//filtra os usuários por descrição de perfil
				String descPerfilAcesso = (String)atributosFiltros.get("descPerfilAcesso");
				log.debug("\tdescPerfilAcesso: "+descPerfilAcesso);
				if(descPerfilAcesso != null && !descPerfilAcesso.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricaoPerfil", descPerfilAcesso, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new PerfilTransformer());
			return (Collection<PerfilView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public Perfil update(Perfil objeto) throws InfrastructureException, Exception {
		try{
			return (Perfil) getSession().merge(objeto);
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

			Criteria c = getSession().createCriteria(Perfil.class);		
			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPerfil"));
			pList.add(Projections.property("nomePerfil"));
			c.setProjection(pList);

			c.addOrder(Order.asc("nomePerfil"));

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
