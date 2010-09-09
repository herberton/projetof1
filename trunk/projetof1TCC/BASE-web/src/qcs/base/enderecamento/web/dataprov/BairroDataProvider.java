package qcs.base.enderecamento.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.persistence.dao.BairroDao;
import qcs.base.enderecamento.persistence.dao.impl.BairroDaoImpl;
import qcs.base.enderecamento.persistence.view.BairroView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class BairroDataProvider extends HibernateDataProvider<BairroView, Bairro, Long> {
	protected static Log log = LogFactory.getLog(BairroDataProvider.class);
	private static final long serialVersionUID = 1L;
	private BairroDao bairroDao;

	public BairroDataProvider(){	}

	@Override
	public BairroView getObjectByPk(Object idPk) {
		for (BairroView documento : getAllItems()) {
			if (documento.getIdBairro().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Tipo de processamento pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (BairroView bairro : getAllItems()) {
			if (bairro.getIdBairro().equals(idPk)) {
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
	 * @return lista de objetos do bairro
	 */
	public List<BairroView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		log.debug("HibernateDataProvider : getObjectByRange");
		List<BairroView> lista = null;
		try{
			lista = (List<BairroView>)getBairroDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);
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
					new FacesMessage("Erro BairroDataProvider : getObjectByRange", e.getMessage()));
			session.getTransaction().rollback();
		}
		return lista;
	}
	
	public List<BairroView> getObjectByFilter(Map<String, Object> atributosFiltros) {
		List<BairroView> lista = null;
		try{
			lista = (List<BairroView>)getBairroDao().listWithFilterToView(atributosFiltros);

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BairroDataProvider : getObjectByFilter", e.getMessage()));
			session.getTransaction().rollback();
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
			setSize(getBairroDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BairroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
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
		setSize(getBairroDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BairroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}
	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
		setSize(getBairroDao().getMaxRows(atributosFiltros));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BairroDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public Bairro alterar(Bairro bairro) throws InfrastructureException, Exception{
		bairro = getBairroDao().update(bairro);
		session.flush();
		return bairro;
	}

	public Bairro incluir(Bairro objeto) throws InfrastructureException, Exception{
		objeto = getBairroDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(Bairro objeto) throws InfrastructureException, Exception{
		getBairroDao().makeTransient(objeto);
		session.flush();
	}	
	
	public Bairro consultar(Long id) {
		try{
			 return getBairroDao().get(id);
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		return null;
	}
	
	public BairroDao getBairroDao() {
		this.bairroDao = new BairroDaoImpl(session);
		return bairroDao;
	}

}