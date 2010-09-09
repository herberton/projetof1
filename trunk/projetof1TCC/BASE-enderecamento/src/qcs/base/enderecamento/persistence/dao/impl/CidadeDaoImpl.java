package qcs.base.enderecamento.persistence.dao.impl;

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

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.dao.CidadeDao;
import qcs.base.enderecamento.persistence.transformer.CidadeTransformer;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class CidadeDaoImpl extends HibernateDaoImpl<Cidade, Long>
implements CidadeDao {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(CidadeDaoImpl.class);

	public CidadeDaoImpl(Session session){
		super(session);
	}

	@Override
	public Collection<Cidade> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		//TODO
		return null;
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("uf", "valTabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCidade"));
			pList.add(Projections.property("nomeCidade"));
			pList.add(Projections.property("valTabAux.nome"));

			c.setProjection(pList);

			if(atributosFiltros != null){
				//filtra o nome da cidade colocando like
				String nome = (String)atributosFiltros.get("nome");
				System.out.println("nome "+nome);
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nomeCidade", nome,MatchMode.ANYWHERE));

				//filtra cidade de acordo com o estado
				Long idValTabAux= (Long)atributosFiltros.get("idValTabAuxUf");
				System.out.println("idValTabAuxUf "+idValTabAux);
				if(idValTabAux != null){
					c.add(Restrictions.eq("valTabAux.idValTabAux", idValTabAux));
				}
				
				//filtra cidade de acordo com o estado
				String nomeUf = (String)atributosFiltros.get("uf");
				log.debug("\tUF: "+nomeUf);
				if(nomeUf != null && !nomeUf.trim().isEmpty()){
					c.add(Restrictions.ilike("valTabAux.nome", nomeUf, MatchMode.ANYWHERE));
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

	@Override
	public Cidade update(Cidade objeto) throws InfrastructureException, Exception {
		return (Cidade) getSession().merge(objeto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> listOfValues() throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValues = new HashMap<Long, String>();
			Criteria c = getSession().createCriteria(Cidade.class);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCidade"));
			pList.add(Projections.property("nomeCidade"));

			c.setProjection(pList);
			c.addOrder(Order.asc("nomeCidade"));

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
	public Collection<CidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("uf", "valTabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCidade"));
			pList.add(Projections.property("nomeCidade"));
			pList.add(Projections.property("valTabAux.nome"));

			c.setProjection(pList);

			if(atributosFiltros != null){
				//filtra o nome da cidade colocando like
				String nome = (String)atributosFiltros.get("nome");
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nomeCidade", nome,MatchMode.ANYWHERE));

				//filtra cidade de acordo com o estado
				Long idValTabAux= (Long)atributosFiltros.get("idValTabAuxUf");
				if(idValTabAux != null && idValTabAux!=0){
					c.add(Restrictions.eq("valTabAux.idValTabAux", idValTabAux));
				}
				
				//filtra cidade de acordo com o estado
				String nomeUf = (String)atributosFiltros.get("uf");
				log.debug("\tUF: "+nomeUf);
				if(nomeUf != null && !nomeUf.trim().isEmpty()){
					c.add(Restrictions.ilike("valTabAux.nome", nomeUf, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new CidadeTransformer());
			return (Collection<CidadeView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CidadeView> listWithFilterToView(
			Map<String, Object> atributosFiltros) throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("uf", "valTabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idCidade"));
			pList.add(Projections.property("nomeCidade"));
			pList.add(Projections.property("valTabAux.nome"));

			c.setProjection(pList);

			if(atributosFiltros != null){
				//filtra o nome da cidade colocando like
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnome: "+nome);
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nomeCidade", nome,MatchMode.ANYWHERE));

				//filtra cidade de acordo com o estado
				Long idValTabAux= (Long)atributosFiltros.get("idValTabAuxUf");
				
				if(idValTabAux != null){
					c.add(Restrictions.eq("valTabAux.idValTabAux", idValTabAux));
				}
				
				//filtra cidade de acordo com o estado
				String nomeUf = (String)atributosFiltros.get("uf");
				log.debug("\tUF: "+nomeUf);
				if(nomeUf != null && !nomeUf.trim().isEmpty()){
					c.add(Restrictions.ilike("valTabAux.nome", nomeUf, MatchMode.ANYWHERE));
				}
			}
			c.setResultTransformer(new CidadeTransformer());
			return (Collection<CidadeView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> listOfValuesByUf(Long codUf) throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValuesByUf = new HashMap<Long, String>();
			Criteria c = getSession().createCriteria(classeEntidade);

			if (codUf != null)
				c.add(Restrictions.eq("idValTabAuxUf", codUf));

			List<Cidade> objetos = c.list();		
			for(Cidade objeto:objetos){
				listOfValuesByUf.put(objeto.getIdCidade(), objeto.getNomeCidade());		
			}

			return listOfValuesByUf;
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}		
}
