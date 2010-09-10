package qcs.base.seguranca.persistence.dao.impl;

import java.util.Collection;
import java.util.Date;
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

import qcs.base.seguranca.PerfilUsuario;
import qcs.base.seguranca.persistence.dao.PerfilUsuarioDao;
import qcs.base.seguranca.persistence.transformer.PerfilUsuarioTransformer;
import qcs.base.seguranca.persistence.view.PerfilUsuarioView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class PerfilUsuarioDaoImpl extends HibernateDaoImpl<PerfilUsuario, Long>
implements PerfilUsuarioDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(PerfilUsuarioDaoImpl.class);

	public PerfilUsuarioDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PerfilUsuario> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)
			throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("usuario", "usu", Criteria.LEFT_JOIN);
			c.createAlias("usu.perfil", "perfUsu", Criteria.LEFT_JOIN);
			c.createAlias("usuarioAtiva", "usuAtiva", Criteria.LEFT_JOIN);
			c.createAlias("usuarioDesativa", "usuDesativa", Criteria.LEFT_JOIN);
			c.createAlias("usuarioAlt", "usuAlt", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPerfilUsuario"));
			pList.add(Projections.property("ativo"));
			pList.add(Projections.property("dataAtivacao"));
			pList.add(Projections.property("dataDesativacao"));
			pList.add(Projections.property("dataUltAlteracao"));
			pList.add(Projections.property("observacao"));
			pList.add(Projections.property("usu.login"));
			pList.add(Projections.property("perfUsu.nomePerfil"));
			pList.add(Projections.property("usuAtiva.login"));
			pList.add(Projections.property("usuDesativa.login"));
			pList.add(Projections.property("usuAlt.login"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("idPerfil listWithFilterToView: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){
					c.add(Restrictions.eq("idPerfilUsuario", idPerfil));
				}

				String usuario = (String)atributosFiltros.get("usuario"); 
				log.debug("usuario listWithFilterToView: "+usuario);
				if(usuario != null && !usuario.trim().isEmpty()){
					c.add(Restrictions.ilike("usu.login", usuario, MatchMode.ANYWHERE));
				}

				Date dataAtivaInicial = (Date)atributosFiltros.get("dataAtivaInicial");
				Date dataAtivaFinal = (Date)atributosFiltros.get("dataAtivaFinal");
				log.debug("dataAtiva listWithFilterToView: "+dataAtivaInicial+ " a " + dataAtivaFinal);
				if(dataAtivaInicial != null && dataAtivaFinal != null){
					c.add(Restrictions.between("dataAtivacao", dataAtivaInicial, dataAtivaFinal));
				}

				Date dataDesativaInicial = (Date)atributosFiltros.get("dataDesativaInicial");
				Date dataDesativaFinal = (Date)atributosFiltros.get("dataDesativaFinal");
				log.debug("dataDesativa listWithFilterToView: "+dataDesativaInicial+ " a " + dataDesativaFinal);
				if(dataDesativaInicial != null && dataDesativaFinal != null){
					c.add(Restrictions.between("dataDesativacao", dataDesativaInicial, dataDesativaFinal));
				}

				Date dataUltInicial = (Date)atributosFiltros.get("dataUltInicial");
				Date dataUltFinal = (Date)atributosFiltros.get("dataUltFinal");
				log.debug("dataUlt listWithFilterToView: "+dataUltInicial+ " a " + dataUltFinal);
				if(dataUltInicial != null && dataUltFinal != null){
					c.add(Restrictions.between("dataUltAlteracao", dataUltInicial, dataUltFinal));
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

			return (Collection<PerfilUsuario>) c.list();
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
			c.createAlias("usuario", "usu", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){

				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("idPerfil listWithFilterToView: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){
					c.add(Restrictions.eq("idPerfilUsuario", idPerfil));
				}

				String usuario = (String)atributosFiltros.get("usuario"); 
				log.debug("usuario listWithFilterToView: "+usuario);
				if(usuario != null && !usuario.trim().isEmpty()){
					c.add(Restrictions.ilike("usu.login", usuario, MatchMode.ANYWHERE));
				}

				Date dataAtivaInicial = (Date)atributosFiltros.get("dataAtivaInicial");
				Date dataAtivaFinal = (Date)atributosFiltros.get("dataAtivaFinal");
				log.debug("dataAtiva listWithFilterToView: "+dataAtivaInicial+ " a " + dataAtivaFinal);
				if(dataAtivaInicial != null && dataAtivaFinal != null){
					c.add(Restrictions.between("dataAtivacao", dataAtivaInicial, dataAtivaFinal));
				}

				Date dataDesativaInicial = (Date)atributosFiltros.get("dataDesativaInicial");
				Date dataDesativaFinal = (Date)atributosFiltros.get("dataDesativaFinal");
				log.debug("dataDesativa listWithFilterToView: "+dataDesativaInicial+ " a " + dataDesativaFinal);
				if(dataDesativaInicial != null && dataDesativaFinal != null){
					c.add(Restrictions.between("dataDesativacao", dataDesativaInicial, dataDesativaFinal));
				}

				Date dataUltInicial = (Date)atributosFiltros.get("dataUltInicial");
				Date dataUltFinal = (Date)atributosFiltros.get("dataUltFinal");
				log.debug("dataUlt listWithFilterToView: "+dataUltInicial+ " a " + dataUltFinal);
				if(dataUltInicial != null && dataUltFinal != null){
					c.add(Restrictions.between("dataUltAlteracao", dataUltInicial, dataUltFinal));
				}		

			}

			c.setProjection(Projections.rowCount());
			return (Integer) c.uniqueResult();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PerfilUsuarioView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("usuario", "usu", Criteria.LEFT_JOIN);
			c.createAlias("usu.perfil", "perfUsu", Criteria.LEFT_JOIN);
			c.createAlias("usuarioAtiva", "usuAtiva", Criteria.LEFT_JOIN);
			c.createAlias("usuarioDesativa", "usuDesativa", Criteria.LEFT_JOIN);
			c.createAlias("usuarioAlt", "usuAlt", Criteria.LEFT_JOIN);
		

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPerfilUsuario"));
			pList.add(Projections.property("ativo"));
			pList.add(Projections.property("dataAtivacao"));
			pList.add(Projections.property("dataDesativacao"));
			pList.add(Projections.property("dataUltAlteracao"));
			pList.add(Projections.property("observacao"));
			pList.add(Projections.property("usu.login"));
			pList.add(Projections.property("perfUsu.nomePerfil"));
			pList.add(Projections.property("usuAtiva.login"));
			pList.add(Projections.property("usuDesativa.login"));
			pList.add(Projections.property("usuAlt.login"));
			c.setProjection(pList);

			if(atributosFiltros != null){

				Long idPerfil = (Long)atributosFiltros.get("idPerfil");
				log.debug("idPerfil listWithFilterToView: "+idPerfil);
				if(idPerfil != null && idPerfil != 0){
					c.add(Restrictions.eq("idPerfilUsuario", idPerfil));
				}

				String usuario = (String)atributosFiltros.get("usuario"); 
				log.debug("usuario listWithFilterToView: "+usuario);
				if(usuario != null && !usuario.trim().isEmpty()){
					c.add(Restrictions.ilike("usu.login", usuario, MatchMode.ANYWHERE));
				}

				Date dataAtivaInicial = (Date)atributosFiltros.get("dataAtivaInicial");
				Date dataAtivaFinal = (Date)atributosFiltros.get("dataAtivaFinal");
				log.debug("dataAtiva listWithFilterToView: "+dataAtivaInicial+ " a " + dataAtivaFinal);
				if(dataAtivaInicial != null && dataAtivaFinal != null){
					c.add(Restrictions.between("dataAtivacao", dataAtivaInicial, dataAtivaFinal));
				}

				Date dataDesativaInicial = (Date)atributosFiltros.get("dataDesativaInicial");
				Date dataDesativaFinal = (Date)atributosFiltros.get("dataDesativaFinal");
				log.debug("dataDesativa listWithFilterToView: "+dataDesativaInicial+ " a " + dataDesativaFinal);
				if(dataDesativaInicial != null && dataDesativaFinal != null){
					c.add(Restrictions.between("dataDesativacao", dataDesativaInicial, dataDesativaFinal));
				}

				Date dataUltInicial = (Date)atributosFiltros.get("dataUltInicial");
				Date dataUltFinal = (Date)atributosFiltros.get("dataUltFinal");
				log.debug("dataUlt listWithFilterToView: "+dataUltInicial+ " a " + dataUltFinal);
				if(dataUltInicial != null && dataUltFinal != null){
					c.add(Restrictions.between("dataUltAlteracao", dataUltInicial, dataUltFinal));
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

			c.setResultTransformer(new PerfilUsuarioTransformer());
			return (Collection<PerfilUsuarioView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

	@Override
	public PerfilUsuario update(PerfilUsuario objeto) throws InfrastructureException, Exception {
		try{
			return (PerfilUsuario) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

}
