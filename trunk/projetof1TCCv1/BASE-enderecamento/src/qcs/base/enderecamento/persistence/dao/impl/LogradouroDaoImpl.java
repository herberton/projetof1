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

import qcs.base.enderecamento.Logradouro;
import qcs.base.enderecamento.persistence.dao.LogradouroDao;
import qcs.base.enderecamento.persistence.transformer.LogradouroTransformer;
import qcs.base.enderecamento.persistence.view.LogradouroView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class LogradouroDaoImpl extends HibernateDaoImpl<Logradouro, Long>
implements LogradouroDao {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(LogradouroDaoImpl.class);

	public LogradouroDaoImpl(Session session){
		super(session);
	}

	@Override
	public Collection<Logradouro> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		//TODO
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<LogradouroView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {

		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("bairro", "bair", Criteria.LEFT_JOIN);
			c.createAlias("cidade", "cid", Criteria.INNER_JOIN);
			c.createAlias("cid.uf", "vta", Criteria.INNER_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idLogradouro"));
			pList.add(Projections.property("cep"));
			pList.add(Projections.property("logradouro"));
			pList.add(Projections.property("bair.nome"));
			pList.add(Projections.property("cid.nomeCidade"));
			pList.add(Projections.property("vta.nome"));
			pList.add(Projections.property("ativo"));

			c.setProjection(pList);

			if(atributosFiltros != null){

				Long idCidade = (Long)atributosFiltros.get("idCidade");
				System.out.println("idCidade:"+idCidade);
				if(idCidade!=null){
					c.add(Restrictions.eq("cid.idCidade", idCidade));
				}

				//filtra o cep colocando like
				String cep = (String)atributosFiltros.get("cep");
				System.out.println("cep:"+cep);
				log.debug("\tcep listWithFilterToView: "+cep);
				if(cep != null && !cep.trim().isEmpty()){
					c.add(Restrictions.ilike("cep", cep, MatchMode.ANYWHERE));
				}

				//filtra o logradouro colocando like
				String logradouro = (String)atributosFiltros.get("logradouro");
				System.out.println("logradouro:"+logradouro);
				log.debug("\tlogradouro listWithFilterToView: "+logradouro);
				if(logradouro != null && !logradouro.trim().isEmpty()){
					c.add(Restrictions.ilike("logradouro",logradouro, MatchMode.ANYWHERE));
				}

				//filtra o bairro colocando like
				String bairro = (String)atributosFiltros.get("bairro");
				System.out.println("bairro:"+bairro);
				log.debug("\tbairro listWithFilterToView: "+bairro);
				if(bairro != null && !bairro.trim().isEmpty()){
					c.add(Restrictions.ilike("bair.nome",bairro, MatchMode.ANYWHERE));
				}

				//filtra o status do cep ('N'=Ativo, 'S'=Inativo) colocando like
				String inativo = (String)atributosFiltros.get("inativo");
				System.out.println("inativo:"+inativo);
				log.debug("\tstatus listWithFilterToView: "+inativo);
				if(inativo != null && !inativo.trim().isEmpty()){
					c.add(Restrictions.ilike("inativo",inativo,MatchMode.ANYWHERE));
				}

				String uf = (String) atributosFiltros.get("uf");
				System.out.println("uf:"+uf);
				log.debug("\tuf listWithFilterToView: "+uf);
				if(uf != null && !uf.trim().isEmpty()){
					c.add(Restrictions.ilike("vta.nome", uf, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new LogradouroTransformer());
			return (Collection<LogradouroView>) c.list();
		}catch (HibernateException ex) {
			ex.printStackTrace();
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception {
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("bairro", "bair", Criteria.LEFT_JOIN);
			c.createAlias("cidade", "cid", Criteria.INNER_JOIN);
			c.createAlias("cid.uf", "vta", Criteria.INNER_JOIN);

			if(atributosFiltros != null){

				Long idCidade = (Long)atributosFiltros.get("idCidade");
				if(idCidade!=null){
					c.add(Restrictions.eq("cid.idCidade", idCidade));
				}

				//filtra o cep colocando like
				String cep = (String)atributosFiltros.get("cep");
				log.debug("\tcep listWithFilterToView: "+cep);
				if(cep != null && !cep.trim().isEmpty()){
					c.add(Restrictions.ilike("cep", cep, MatchMode.ANYWHERE));
				}

				//filtra o logradouro colocando like
				String logradouro = (String)atributosFiltros.get("logradouro");
				log.debug("\tlogradouro listWithFilterToView: "+logradouro);
				if(logradouro != null && !logradouro.trim().isEmpty()){
					c.add(Restrictions.ilike("logradouro",logradouro, MatchMode.ANYWHERE));
				}

				//filtra o bairro colocando like
				String bairro = (String)atributosFiltros.get("bairro");
				log.debug("\tbairro listWithFilterToView: "+bairro);
				if(bairro != null && !bairro.trim().isEmpty()){
					c.add(Restrictions.ilike("bair.nome",bairro, MatchMode.ANYWHERE));
				}

				//filtra o status do cep ('N'=Ativo, 'S'=Inativo) colocando like
				String inativo = (String)atributosFiltros.get("inativo");
				log.debug("\tstatus listWithFilterToView: "+inativo);
				if(inativo != null && !inativo.trim().isEmpty()){
					c.add(Restrictions.ilike("ativo",inativo,MatchMode.ANYWHERE));
				}

				String uf = (String) atributosFiltros.get("uf");
				log.debug("\tuf listWithFilterToView: "+uf);
				if(uf != null && !uf.trim().isEmpty()){
					c.add(Restrictions.ilike("vta.nome", uf, MatchMode.ANYWHERE));
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
	public Logradouro update(Logradouro objeto) throws InfrastructureException, Exception {
		try{
			return (Logradouro) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
}
