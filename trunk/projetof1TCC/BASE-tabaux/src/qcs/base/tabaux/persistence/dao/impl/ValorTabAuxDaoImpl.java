package qcs.base.tabaux.persistence.dao.impl;

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

import qcs.base.constantes.TabelasAuxiliares;
import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.dao.ValorTabAuxDao;
import qcs.base.tabaux.persistence.transformer.ValorTabAuxTransformer;
import qcs.base.tabaux.persistence.view.ValorTabAuxView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class ValorTabAuxDaoImpl extends HibernateDaoImpl<ValorTabAux, Long>
implements ValorTabAuxDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(ValorTabAuxDaoImpl.class);

	public ValorTabAuxDaoImpl(Session session){
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ValorTabAux> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {
		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			if(atributosFiltros != null){
				//filtra o nome do tipo colocando like
				String nome = (String)atributosFiltros.get("nomeValorTabAux");
				log.debug("\tnomeValorTabAux: "+nome);
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nome,MatchMode.ANYWHERE));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}

			return (Collection<ValorTabAux>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<ValorTabAuxView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception {

		Criteria c = getSession().createCriteria(classeEntidade);
		c.createAlias("tabelaAuxiliar", "tabAux", Criteria.LEFT_JOIN);

		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("idValTabAux"));
		pList.add(Projections.property("tabAux.nomeTabAux"));
		pList.add(Projections.property("nome"));		
		pList.add(Projections.property("observacao"));
		c.setProjection(pList);

		try{
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			
			if(atributosFiltros != null){
				//filtra valores tabela auxiliares por nome
				Long idTabAux = (Long)atributosFiltros.get("idTabAux");
				log.info("\tidTabaux: "+idTabAux);
				if(idTabAux != null && idTabAux != 0)
					c.add(Restrictions.eq("tabAux.idTabAux",idTabAux));
			}

			if(atributosFiltros != null){
				//filtra valores tabela auxiliares por nome
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnomeValorTabAux: "+nome);
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nome,MatchMode.ANYWHERE));
			}

			if(atributosFiltros != null){
				//filtra valores tabela auxiliares por descrição
				String observacao = (String)atributosFiltros.get("observacao");
				log.debug("\tdescricaoValorTabAux: "+observacao);
				if(observacao != null && !observacao.trim().isEmpty())
					c.add(Restrictions.ilike("observacao",observacao,MatchMode.ANYWHERE));
			}		


			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			
			c.setResultTransformer(new ValorTabAuxTransformer());

			return (Collection<ValorTabAuxView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException {

		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tabelaAuxiliar", "tabAux", Criteria.LEFT_JOIN);

			if(atributosFiltros != null){	

				//filtra valores tabela auxiliares por nome
				Long idTabAux = (Long)atributosFiltros.get("idTabAux");
				log.info("\tidTabaux: "+idTabAux);
				if(idTabAux != null && idTabAux != 0)
					c.add(Restrictions.eq("tabAux.idTabAux",idTabAux));
				
				//filtra valores tabela auxiliares por nome
				String nome = (String)atributosFiltros.get("nome");
				log.debug("\tnomeValorTabAux: "+nome);
				if(nome != null && !nome.trim().isEmpty())
					c.add(Restrictions.ilike("nome",nome,MatchMode.ANYWHERE));

				//filtra valores tabela auxiliares por descrição
				String observacao = (String)atributosFiltros.get("observacao");
				log.debug("\tdescricaoValorTabAux: "+observacao);
				if(observacao != null && !observacao.trim().isEmpty())
					c.add(Restrictions.ilike("observacao",observacao,MatchMode.ANYWHERE));
			}	
			c.setProjection(Projections.rowCount());
			return (Integer)c.uniqueResult();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}			

	}

	@Override
	public ValorTabAux update(ValorTabAux objeto) throws InfrastructureException, Exception {
		try{
			return (ValorTabAux) getSession().merge(objeto);
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}catch(Exception e){
			throw e;
		}
	}
	//UF
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> listOfValuesUf() throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValues = new HashMap<Long, String>();

			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tabelaAuxiliar", "tabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idValTabAux"));
			pList.add(Projections.property("nome"));
			c.setProjection(pList);

			c.add(Restrictions.eq("tabAux.idTabAux", TabelasAuxiliares.UF));

			c.addOrder(Order.asc("nome"));

			List<Object[]>objetos = c.list();

			for(Object[] objeto : objetos){
				listOfValues.put((Long)objeto[0], (String)objeto[1]);
			}
			return listOfValues;
		}catch (HibernateException ex) {
			ex.printStackTrace();
			throw new InfrastructureException(ex);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	//Tipo de Logradouro
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> listOfValuesTipoLog() throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValues = new HashMap<Long, String>();

			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tabelaAuxiliar", "tabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idValTabAux"));
			pList.add(Projections.property("nome"));
			c.setProjection(pList);

			c.add(Restrictions.eq("tabAux.idTabAux", TabelasAuxiliares.TIPOLOGRADOURO));

			c.addOrder(Order.asc("nome"));

			List<Object[]>objetos = c.list();

			for(Object[] objeto : objetos){
				listOfValues.put((Long)objeto[0], (String)objeto[1]);
			}
			return listOfValues;
		}catch (HibernateException ex) {
			ex.printStackTrace();
			throw new InfrastructureException(ex);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	//Tipo de Logradouro
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> listOfValuesTipoPessoa() throws InfrastructureException, Exception {
		try{
			Map<Long, String> listOfValues = new HashMap<Long, String>();

			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tabelaAuxiliar", "tabAux", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idValTabAux"));
			pList.add(Projections.property("nome"));
			c.setProjection(pList);

			c.add(Restrictions.eq("tabAux.idTabAux", TabelasAuxiliares.TIPO_PESSOA));

			c.addOrder(Order.asc("nome"));

			List<Object[]>objetos = c.list();

			for(Object[] objeto : objetos){
				listOfValues.put((Long)objeto[0], (String)objeto[1]);
			}
			return listOfValues;
		}catch (HibernateException ex) {
			ex.printStackTrace();
			throw new InfrastructureException(ex);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

}
