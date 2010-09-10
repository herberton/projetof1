package qcs.base.enderecamento.persistence.dao.impl;

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

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.persistence.dao.BairroDao;
import qcs.base.enderecamento.persistence.transformer.BairroTransformer;
import qcs.base.enderecamento.persistence.view.BairroView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class BairroDaoImpl extends HibernateDaoImpl<Bairro, Long>
implements BairroDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(BairroDaoImpl.class);

	public BairroDaoImpl(Session session){
		super(session);
	}

	@Override
	public Collection<Bairro> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) {
		//TODO
		return null;
	}
	
	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("cidade", "cid", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBairro"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("abrev"));
			pList.add(Projections.property("cid.nomeCidade"));
			
			c.setProjection(pList);
			
			if(atributosFiltros != null){
				String nome = (String)atributosFiltros.get("nome");
				if(nome!=null && !nome.isEmpty())
					c.add(Restrictions.or(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE), Restrictions.ilike("abrev", nome, MatchMode.ANYWHERE)));
				String nomeCidade = (String)atributosFiltros.get("nomeCidade");
				if(nomeCidade!=null && !nomeCidade.isEmpty())
					c.add(Restrictions.ilike("cid.nomeCidade", nomeCidade, MatchMode.ANYWHERE));
			}
			c.setProjection(Projections.rowCount());
			return (Integer)c.uniqueResult();
			
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BairroView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) {
		log.debug("BairroDaoImpl : listWithFilterToView\n\tparâmetros:");
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("cidade", "cid", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBairro"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("abrev"));
			pList.add(Projections.property("cid.nomeCidade"));
			
			c.setProjection(pList);
			
			if(atributosFiltros != null){
				String nome = (String)atributosFiltros.get("nome");
				if(nome!=null && !nome.isEmpty())
					c.add(Restrictions.or(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE), Restrictions.ilike("abrev", nome, MatchMode.ANYWHERE)));
				String nomeCidade = (String)atributosFiltros.get("nomeCidade");
				if(nomeCidade!=null && !nomeCidade.isEmpty())
					c.add(Restrictions.ilike("cid.nomeCidade", nomeCidade, MatchMode.ANYWHERE));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			
			c.setResultTransformer(new BairroTransformer());
			return (Collection<BairroView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<BairroView> listWithFilterToView(
			Map<String, Object> atributosFiltros) {
		log.debug("BairroDaoImpl : listWithFilterToView\n\tparâmetros:");
		try{	
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("cidade", "cid", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idBairro"));
			pList.add(Projections.property("nome"));
			pList.add(Projections.property("abrev"));
			pList.add(Projections.property("cid.nomeCidade"));
			
			c.setProjection(pList);
			
			if(atributosFiltros != null){
				String nome = (String)atributosFiltros.get("nome");
				if(nome!=null && !nome.isEmpty())
					c.add(Restrictions.or(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE), Restrictions.ilike("abrev", nome, MatchMode.ANYWHERE)));
				String nomeCidade = (String)atributosFiltros.get("nomeCidade");
				if(nomeCidade!=null && !nomeCidade.isEmpty())
					c.add(Restrictions.ilike("cid.nomeCidade", nomeCidade, MatchMode.ANYWHERE));
			}
			
			c.setResultTransformer(new BairroTransformer());
			return (Collection<BairroView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}	
	
	@Override
	public Bairro update(Bairro objeto) {
		objeto = (Bairro) getSession().merge(objeto);
		return null;
	}

}
