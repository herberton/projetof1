package qcs.base.enderecamento.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.dao.CidadeDao;
import qcs.base.enderecamento.persistence.dao.impl.CidadeDaoImpl;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class CidadeDataProvider extends HibernateDataProvider<CidadeView, Cidade, Long> {
	protected static Log log = LogFactory.getLog(CidadeDataProvider.class);
	private static final long serialVersionUID = 1L;
	private CidadeDao cidadeDao;


	public CidadeDataProvider(){ }

	@Override
	public CidadeView getObjectByPk(Object idPk) {
		for (CidadeView documento : getAllItems()) {
			if (documento.getIdCidade().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Cidade pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (CidadeView cidade : getAllItems()) {
			if (cidade.getIdCidade().equals(idPk)) {
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
	 * @return lista de objetos do cidade
	 */
	public List<CidadeView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		log.debug("CidadeDataProvider : getObjectByRange");
		List<CidadeView> lista = null;
		try{
			lista = (List<CidadeView>)getCidadeDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);
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
					new FacesMessage("Erro CidadeDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getCidadeDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CidadeDataProvider : getRowCount", e.getMessage()));
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
			setSize(getCidadeDao().getMaxRows(atributosFiltros, valores));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CidadeDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}
	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getCidadeDao().getMaxRows(atributosFiltros));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CidadeDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public Cidade alterar(Cidade objeto) throws InfrastructureException, Exception{
			objeto = getCidadeDao().update(objeto);
			session.flush();
		return objeto;
	}

	public Cidade incluir(Cidade objeto) throws InfrastructureException, Exception{
			objeto = getCidadeDao().add(objeto);
			session.flush();
		return objeto;
	}

	public void excluir(Cidade objeto) throws InfrastructureException, Exception{
			getCidadeDao().makeTransient(objeto);
			session.flush();
	}

	public CidadeDao getCidadeDao() {
		this.cidadeDao = new CidadeDaoImpl(session);
		return cidadeDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public Cidade consultar(Long id) throws InfrastructureException, Exception {
		return getCidadeDao().get(id);

	}

	public List<CidadeView> getObjectByFilter(Map<String, Object> atributosFiltros) {
		List<CidadeView> lista = null;
		try{
			lista = (List<CidadeView>)getCidadeDao().listWithFilterToView(atributosFiltros);
			System.out.println("lista");
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CidadeDataProvider : getObjectByFilter", e.getMessage()));
			session.getTransaction().rollback();
		}
		return lista;
		
	}

}
