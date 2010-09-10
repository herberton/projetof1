package qcs.base.pessoa.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.persistence.dao.PessoaDao;
import qcs.base.pessoa.persistence.dao.impl.PessoaDaoImpl;
import qcs.base.pessoa.persistence.view.PessoaView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class PessoaDataProvider extends HibernateDataProvider<PessoaView, Pessoa, Long> {
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(PessoaDataProvider.class);
	
	private PessoaDao PessoaDao;

	public PessoaDataProvider(){}

	@Override
	public PessoaView getObjectByPk(Object idPk) {
		for (PessoaView obj : getAllItems()) {
			if (obj.getIdPessoa().equals(idPk)) {
				return obj;
			}
		}
		throw new RuntimeException("Pessoa pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (PessoaView obj : getAllItems()) {
			if (obj.getIdPessoa().equals(idPk)) {				
				return true;
			}
		}
		return false;
	}


	/**
	 * M�todo responsavel pelo retorno de uma range
	 * da objetos de acordo com a pagina��o da DataTable,
	 * principal m�todo pela p�gina��o sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordena��o
	 * @param descending Modo de ordena��o
	 * @return lista de objetos do Pessoa
	 */
	public List<PessoaView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<PessoaView> lista = null;
		try{
			lista = (List<PessoaView>)getPessoaDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);
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
					new FacesMessage("Erro PessoaDataProvider : getObjectByRange", e.getMessage()));
		}
		return lista;
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * ser� necessario sobrecarregar o m�todo.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 */
	public int getRowCount() {
		try{
			setSize(getPessoaDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PessoaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade utilizando os filtros.
	 * @param atributosFiltros - s�o os filtros que forem preenchidos pelo usu�rio na tela de pesquisa:
	 * <br>o primeiro par�metro do map � uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo � a express�o de compara��o
	 * @param valores s�o os valores que dever�o ser comparados
	 * <br>o primeiro par�metro do map � uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo � o valor que deve ser comparado
	 * @return int com quantidade de linhas da tabela.
	 */	
	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object>valores) {
		try{
			setSize(getPessoaDao().getMaxRows(atributosFiltros, valores));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PessoaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getPessoaDao().getMaxRows(atributosFiltros));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PessoaDataProvider : getRowCount", e.getMessage()));
		}		
		return getSize();
	}

	public Pessoa consultar(Long id) throws InfrastructureException, Exception{
		return getPessoaDao().get(id);
	}

	public Pessoa alterar(Pessoa objeto) throws InfrastructureException, Exception{
		objeto = getPessoaDao().update(objeto);
		session.flush();

		return objeto;
	}

	public Pessoa incluir(Pessoa objeto) throws InfrastructureException, Exception{
		objeto = getPessoaDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Pessoa objeto) throws InfrastructureException, Exception{
		getPessoaDao().makeTransient(objeto);
		session.flush();
	}

	public PessoaDao getPessoaDao() {
		this.PessoaDao = new PessoaDaoImpl(session);
		return PessoaDao;
	}

	public void setPessoaDao(PessoaDao PessoaDao) {
		this.PessoaDao = PessoaDao;
	}	
}
