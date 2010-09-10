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

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.dao.UsuarioDao;
import qcs.base.seguranca.persistence.transformer.UsuarioTransformer;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class UsuarioDaoImpl extends HibernateDaoImpl<Usuario, Long>
implements UsuarioDao {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(UsuarioDaoImpl.class);

	public UsuarioDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Usuario> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(Usuario.class);
			c.createAlias("pessoa", "pes", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){

				String login = (String)atributosFiltros.get("login");
				if(login != null && !login.trim().isEmpty())
					c.add(Restrictions.ilike("login", login, MatchMode.ANYWHERE));

				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("pes.cpf", cpfOuCnpj),Restrictions.eq("pes.cnpj", cpfOuCnpj)));

				String nomePessoa = (String)atributosFiltros.get("nomePessoa");
				if(nomePessoa != null && !nomePessoa.trim().isEmpty())
					c.add(Restrictions.ilike("pes.nomePessoa", nomePessoa, MatchMode.ANYWHERE));

				String status = (String)atributosFiltros.get("status");
				if(status != null && !status.trim().isEmpty())
					c.add(Restrictions.eq("status", status));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			log.debug("ordenando por ToView: "+orderField);
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			return (Collection<Usuario>)c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(Usuario.class);
			c.createAlias("pessoa", "pes", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){

				String login = (String)atributosFiltros.get("login");
				if(login != null && !login.trim().isEmpty())
					c.add(Restrictions.ilike("login", login, MatchMode.ANYWHERE));

				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("pes.cpf", cpfOuCnpj),Restrictions.eq("pes.cnpj", cpfOuCnpj)));

				String nomePessoa = (String)atributosFiltros.get("nomePessoa");
				if(nomePessoa != null && !nomePessoa.trim().isEmpty())
					c.add(Restrictions.ilike("pes.nomePessoa", nomePessoa, MatchMode.ANYWHERE));

				String status = (String)atributosFiltros.get("status");
				if(status != null && !status.trim().isEmpty())
					c.add(Restrictions.eq("status", status));
			}

			c.setProjection(Projections.rowCount());
			return (Integer)c.uniqueResult();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	public Usuario buscar(String usuario) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(Usuario.class);
			c.add(Restrictions.eq("login", usuario));
			return (Usuario)c.uniqueResult();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<UsuarioView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(Usuario.class);
			c.createAlias("pessoa", "pes", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idUsuario"));
			pList.add(Projections.property("pes.nomePessoa"));
			pList.add(Projections.property("login"));
			pList.add(Projections.property("pes.cpf"));
			pList.add(Projections.property("pes.cnpj"));
			pList.add(Projections.property("bloqueado"));
			pList.add(Projections.property("ativo"));
			

			c.setProjection(pList);

			if(atributosFiltros != null){

				String login = (String)atributosFiltros.get("login");
				if(login != null && !login.trim().isEmpty())
					c.add(Restrictions.ilike("login", login, MatchMode.ANYWHERE));

				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("pes.cpf", cpfOuCnpj),Restrictions.eq("pes.cnpj", cpfOuCnpj)));

				String nomePessoa = (String)atributosFiltros.get("nomePessoa");
				if(nomePessoa != null && !nomePessoa.trim().isEmpty())
					c.add(Restrictions.ilike("pes.nomePessoa", nomePessoa, MatchMode.ANYWHERE));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			log.debug("ordenando por ToView: "+orderField);
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			c.setResultTransformer(new UsuarioTransformer());
			return (Collection<UsuarioView>)c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}		
	}

	@Override
	public Usuario update(Usuario objeto) throws InfrastructureException, Exception {
		try{
			return (Usuario) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
}
