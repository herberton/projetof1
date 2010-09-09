package qcs.base.pessoa.persistence.dao.impl;

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

import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.persistence.dao.PessoaDao;
import qcs.base.pessoa.persistence.transformer.LovPessoaTransformer;
import qcs.base.pessoa.persistence.transformer.PessoaTransformer;
import qcs.base.pessoa.persistence.view.LovPessoaView;
import qcs.base.pessoa.persistence.view.PessoaView;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.template.hibernate.impl.HibernateDaoImpl;

public class PessoaDaoImpl extends HibernateDaoImpl<Pessoa, Long>
implements PessoaDao {

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(PessoaDaoImpl.class);

	public PessoaDaoImpl(Session session){
		super(session);
	}

	@Override
	public Collection<Pessoa> listWithFilter(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception{
		log.debug("PessoaDaoImpl : listWithFilter\n\tparâmetros:");
		//TODO
		return null;
	}
	
	@Override
	public int getMaxRows(Map<String, Object> atributosFiltros)throws InfrastructureException, Exception{
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tipoPessoa", "tipPes", Criteria.LEFT_JOIN);
			
			if(atributosFiltros != null){
				//filtra o nome do Pessoa colocando like
				String nomePessoa = (String)atributosFiltros.get("nomePessoa");
				log.debug("Pessoa getMaxRows: "+nomePessoa);
				if(nomePessoa != null && !nomePessoa.trim().isEmpty()){
					c.add(Restrictions.ilike("nomePessoa", nomePessoa, MatchMode.ANYWHERE));
				}

				//filtra o cpf e cnpj da Pessoa colocando eq
				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("cpf", cpfOuCnpj),Restrictions.eq("cnpj", cpfOuCnpj)));
				
				//filtra o id da Pessoa colocando eq
				Long idPessoa = (Long)atributosFiltros.get("idPessoa");
				log.debug("idPessoa getMaxRows: "+idPessoa);
				if(idPessoa != null && idPessoa!= 0){
					c.add(Restrictions.eq("idPessoa", idPessoa));
				}

				// a situação do Pessoa
				String ativo = (String)atributosFiltros.get("ativo");
				log.debug("Situação getMaxRows: "+ativo);
				if(ativo != null && !ativo.trim().isEmpty()){
					c.add(Restrictions.eq("ativo", ativo));
				}
				
			}
			c.setProjection(Projections.rowCount());
			return (Integer)c.uniqueResult();
			
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PessoaView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending) throws InfrastructureException, Exception{
		log.debug("PessoaDaoImpl : listWithFilterToView\n\tparâmetros:");
		try{	
			log.debug("Listar "+classeEntidade+" de "+first+" até "+max+".");
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tipoPessoa", "tipPes", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPessoa"));
			pList.add(Projections.property("nomePessoa"));
			pList.add(Projections.property("cnpj"));
			pList.add(Projections.property("cpf"));
			pList.add(Projections.property("sexo"));
			pList.add(Projections.property("tipPes.nome"));
			pList.add(Projections.property("ativo"));
			pList.add(Projections.property("rgNum"));
			pList.add(Projections.property("rgDig"));
			c.setProjection(pList);
			
			if(atributosFiltros != null){
				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("cpf", cpfOuCnpj),Restrictions.eq("cnpj", cpfOuCnpj)));
				
				String ativo = (String)atributosFiltros.get("ativo");
				log.debug("Ativo listWithFilterToView: " +ativo);
				if(ativo != null && !ativo.isEmpty()){
					c.add(Restrictions.eq("ativo", ativo));
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

			log.debug(">>> query: "+c.toString());
			c.setResultTransformer(new PessoaTransformer());
			return (Collection<PessoaView>) c.list();

		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}	

	@SuppressWarnings("unchecked")
	@Override
	public Collection<LovPessoaView> listOfValuesWithFilter(
			Map<String, Object> atributosFiltros, int first, int max, String orderField,
			boolean descending)throws InfrastructureException, Exception{
		log.debug("PessoaDaoImpl : listOfValuesWihtFilter\n\tparâmetros:");
		try{
			Criteria c = getSession().createCriteria(classeEntidade);
			c.createAlias("tipoPessoa", "tipPes", Criteria.LEFT_JOIN);

			ProjectionList pList = Projections.projectionList();
			pList.add(Projections.property("idPessoa"));
			pList.add(Projections.property("nomePessoa"));
			pList.add(Projections.property("cpf"));
			pList.add(Projections.property("cnpj"));
			pList.add(Projections.property("tipPes.nome"));
						
			c.setProjection(pList);
	
			if(atributosFiltros != null){
				Long idTipPes = (Long)atributosFiltros.get("idTipPes");
				log.debug("Tipo Pessoa listOfValuesWihtFilter: "+idTipPes);
				if(idTipPes != null && idTipPes!=0){
					c.add(Restrictions.eq("tipPes.idValTabAux", idTipPes));
				}

				//filtra o nome do Pessoa colocando like
				String nomePessoa = (String)atributosFiltros.get("nomePessoa");
				log.debug("Nome Pessoa listOfValuesWihtFilter: "+nomePessoa);
				if(nomePessoa != null && !nomePessoa.trim().isEmpty()){
					c.add(Restrictions.ilike("nomePessoa", nomePessoa, MatchMode.ANYWHERE));
				}

				//filtra o cpf e cnpj da Pessoa colocando eq
				String cpfOuCnpj = (String)atributosFiltros.get("cpfOuCnpj");
				if(cpfOuCnpj != null && !cpfOuCnpj.isEmpty())
					c.add(
							Restrictions.or(Restrictions.eq("cpf", cpfOuCnpj),Restrictions.eq("cnpj", cpfOuCnpj)));
				
				//filtra o id do Pessoa colocando eq
				Long idPessoa = (Long)atributosFiltros.get("idPessoa");
				log.debug("idPessoa listOfValuesWihtFilter: "+idPessoa);
				if(idPessoa != null && idPessoa!= 0){
					c.add(Restrictions.eq("idPessoa", idPessoa));
				}
				
				// a situação do Pessoa
				String situacao = (String)atributosFiltros.get("ativo");
				log.debug("Situação listOfValuesWihtFilter: "+situacao);
				if(situacao != null && !situacao.trim().isEmpty()){
					c.add(Restrictions.eq("ativo", situacao));
				}
			}
			//se houver campo de ordenação este é incluído na consulta
			if(orderField != null){
				c.addOrder(descending ? Order.desc(orderField) : Order.asc(orderField));
			}
			//informa qual a primeira posição para retorno
			c.setFirstResult(first);
			//informa quantos registros a partir da posição inicial devem ser retornados
			c.setMaxResults(max);

			c.setResultTransformer(new LovPessoaTransformer());
			return (Collection<LovPessoaView>) c.list();
		}catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}	
	
	@Override
	public Pessoa update(Pessoa objeto) throws InfrastructureException, Exception{
		objeto = (Pessoa) getSession().merge(objeto);
		return null;
	}

}
