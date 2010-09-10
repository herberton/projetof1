package qcs.base.tabaux.persistence.dao.impl;

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

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.persistence.dao.TabelaAuxiliarDao;
import qcs.base.tabaux.persistence.transformer.TabelaAuxiliarTransformer;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class TabelaAuxiliarDaoImpl extends HibernateDaoImpl<TabelaAuxiliar, Long>
implements TabelaAuxiliarDao {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(TabelaAuxiliarDaoImpl.class);

	public TabelaAuxiliarDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TabelaAuxiliar> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		log.debug("TabelaAuxiliarDaoImpl : listWithFilter\n\tparâmetros:");
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			if(atributosFiltros != null){


				//filtra tabela auxiliar por ID da tabela
				Long idTabelaAuxiliar = (Long)atributosFiltros.get("idTabelaAuxiliar");
				log.debug("idTabelaAuxiliar: "+idTabelaAuxiliar);
				if(idTabelaAuxiliar != null)
					c.add(Restrictions.eq("idTabAux", idTabelaAuxiliar));				


				//filtra tabela auxiliar por descrição da tabela
				String descTabelaAuxiliar = (String)atributosFiltros.get("descTabelaAuxiliar");
				log.debug("descTabelaAuxiliar: "+descTabelaAuxiliar);
				if(descTabelaAuxiliar != null && !descTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descTabelaAuxiliar, MatchMode.ANYWHERE));
				}


				//filtra tabela auxiliar por nome da tabela
				String nomeTabelaAuxiliar = (String)atributosFiltros.get("nomeTabelaAuxiliar");
				log.debug("nomeTabelaAuxiliar: "+nomeTabelaAuxiliar);
				if(nomeTabelaAuxiliar != null && !nomeTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("nomeTabAux", nomeTabelaAuxiliar, MatchMode.ANYWHERE));
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

			return (Collection<TabelaAuxiliar>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TabelaAuxiliarView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending)throws InfrastructureException, Exception {
		log.debug("TabelaAuxiliarDaoImpl : listWithFilterToView\n\tparâmetros:");
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idTabAux"));
			pList.add(Projections.property("nomeTabAux"));
			pList.add(Projections.property("descricao"));

			c.setProjection(pList);

			if(atributosFiltros != null){

				//filtra tabela auxiliar por ID da tabela
				Long idTabelaAuxiliar = (Long)atributosFiltros.get("idTabelaAuxiliar");
				log.debug("idTabelaAuxiliar: "+idTabelaAuxiliar);
				if(idTabelaAuxiliar != null)
					c.add(Restrictions.eq("idTabAux", idTabelaAuxiliar));				


				//filtra tabela auxiliar por descrição da tabela
				String descTabelaAuxiliar = (String)atributosFiltros.get("descTabelaAuxiliar");
				log.debug("descTabelaAuxiliar: "+descTabelaAuxiliar);
				if(descTabelaAuxiliar != null && !descTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descTabelaAuxiliar, MatchMode.ANYWHERE));
				}


				//filtra tabela auxiliar por nome da tabela
				String nomeTabelaAuxiliar = (String)atributosFiltros.get("nomeTabelaAuxiliar");
				log.debug("nomeTabelaAuxiliar: "+nomeTabelaAuxiliar);
				if(nomeTabelaAuxiliar != null && !nomeTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("nomeTabAux", nomeTabelaAuxiliar, MatchMode.ANYWHERE));
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

			c.setResultTransformer(new TabelaAuxiliarTransformer());
			return (Collection<TabelaAuxiliarView>) c.list();

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

				//filtra tabela auxiliar por ID da tabela
				Long idTabelaAuxiliar = (Long)atributosFiltros.get("idTabelaAuxiliar");
				log.debug("idTabelaAuxiliar: "+idTabelaAuxiliar);
				if(idTabelaAuxiliar != null)
					c.add(Restrictions.eq("idTabAux", idTabelaAuxiliar));				


				//filtra tabela auxiliar por descrição da tabela
				String descTabelaAuxiliar = (String)atributosFiltros.get("descTabelaAuxiliar");
				log.debug("descTabelaAuxiliar: "+descTabelaAuxiliar);
				if(descTabelaAuxiliar != null && !descTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("descricao", descTabelaAuxiliar, MatchMode.ANYWHERE));
				}


				//filtra tabela auxiliar por nome da tabela
				String nomeTabelaAuxiliar = (String)atributosFiltros.get("nomeTabelaAuxiliar");
				log.debug("nomeTabelaAuxiliar: "+nomeTabelaAuxiliar);
				if(nomeTabelaAuxiliar != null && !nomeTabelaAuxiliar.trim().isEmpty()){				
					c.add(Restrictions.ilike("nomeTabAux", nomeTabelaAuxiliar, MatchMode.ANYWHERE));
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

	@Override
	public TabelaAuxiliar update(TabelaAuxiliar objeto)throws InfrastructureException, Exception {
		try{
			return (TabelaAuxiliar) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}	

}
