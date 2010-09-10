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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
