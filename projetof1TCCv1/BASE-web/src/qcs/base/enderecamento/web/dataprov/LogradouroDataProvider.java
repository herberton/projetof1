package qcs.base.enderecamento.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Logradouro;
import qcs.base.enderecamento.persistence.dao.LogradouroDao;
import qcs.base.enderecamento.persistence.dao.impl.LogradouroDaoImpl;
import qcs.base.enderecamento.persistence.view.LogradouroView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class LogradouroDataProvider extends HibernateDataProvider<LogradouroView, Logradouro, Long> {
	protected static Log log = LogFactory.getLog(LogradouroDataProvider.class);
	private static final long serialVersionUID = 1L;
	private LogradouroDao logradouroDao;

	public LogradouroDataProvider(){ }

	@Override
	public LogradouroView getObjectByPk(Object idPk) {
		for (LogradouroView documento : getAllItems()) {
			if (documento.getIdLogradouro().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Logradouro pk=" + idPk.toString()
				+ " não encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (LogradouroView cep : getAllItems()) {
			if (cep.getIdLogradouro().equals(idPk)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
	 * @return lista de objetos do cep
	 */
	public List<LogradouroView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		log.debug("LogradouroDataProvider : getObjectByRange");
		List<LogradouroView> lista = null;
		try{
			lista = (List<LogradouroView>)getLogradouroDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);
			if(lista!=null){
				while(lista.size() < numberOfRows){
					lista.add(classeObject.newInstance());
				}
			}

			setAllItems(lista);
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LogradouroDataProvider : getObjectByRange", e.getMessage()));
			session.getTransaction().rollback();
		}
		return lista;
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getLogradouroDao().getMaxRows(atributosFiltros));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LogradouroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}


	/**
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 */
	public int getRowCount() {
		try{
			setSize(getLogradouroDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LogradouroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	/**
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade utilizando os filtros.
	 * @param atributosFiltros - são os filtros que forem preenchidos pelo usuário na tela de pesquisa:
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é a expressão de comparação
	 * @param valores são os valores que deverão ser comparados
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é o valor que deve ser comparado
	 * @return int com quantidade de linhas da tabela.
	 */	
	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object>valores) {
		try{
			setSize(getLogradouroDao().getMaxRows(atributosFiltros, valores));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LogradouroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public Logradouro alterar(Logradouro objeto) throws InfrastructureException, Exception{
		objeto = getLogradouroDao().update(objeto);
		session.flush();

		return objeto;
	}

	public Logradouro incluir(Logradouro objeto) throws InfrastructureException, Exception{
		objeto = getLogradouroDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Logradouro objeto) throws InfrastructureException, Exception{
		getLogradouroDao().makeTransient(objeto);
		session.flush();
	}	

	public LogradouroDao getLogradouroDao() {
		this.logradouroDao = new LogradouroDaoImpl(session);
		return logradouroDao;
	}

	public void setLogradouroDao(LogradouroDao cepDao) {
		this.logradouroDao = cepDao;
	}

	public Logradouro consultar(Long id) throws InfrastructureException, Exception {
		return getLogradouroDao().get(id);
	}

}
