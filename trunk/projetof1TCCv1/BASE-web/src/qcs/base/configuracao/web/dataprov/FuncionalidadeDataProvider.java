package qcs.base.configuracao.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.Funcionalidade;
import qcs.base.modulo.persistence.dao.FuncionalidadeDao;
import qcs.base.modulo.persistence.dao.impl.FuncionalidadeDaoImpl;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.FuncionalidadeView;

public class FuncionalidadeDataProvider extends HibernateDataProvider<FuncionalidadeView, Funcionalidade, Long> {
	private static final long serialVersionUID = 1L;
	private FuncionalidadeDao funcionalidadeDao;

	@Override
	public FuncionalidadeView getObjectByPk(Object idPk) {
		for (FuncionalidadeView documento : getAllItems()) {
			if (documento.getIdFuncionalidade().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Funcionalidade pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getFuncionalidadeDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro FuncionalidadeDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (FuncionalidadeView funcionalidade : getAllItems()) {
			if (funcionalidade.getIdFuncionalidade().equals(idPk)) {
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
	 * @return lista de objetos do funcionalidade
	 */
	public List<FuncionalidadeView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<FuncionalidadeView> lista = null;
		try{
			lista = (List<FuncionalidadeView>)getFuncionalidadeDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro FuncionalidadeDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getFuncionalidadeDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro FuncionalidadeDataProvider : getRowCount", e.getMessage()));
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
			setSize(getFuncionalidadeDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro FuncionalidadeDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public FuncionalidadeDao getFuncionalidadeDao() {
		this.funcionalidadeDao = new FuncionalidadeDaoImpl(session);
		return funcionalidadeDao;
	}

	public void setFuncionalidadeDao(FuncionalidadeDao funcionalidadeDao) {
		this.funcionalidadeDao = funcionalidadeDao;
	}

	public Funcionalidade alterar(Funcionalidade objeto) throws InfrastructureException, Exception{
		objeto = getFuncionalidadeDao().update(objeto);
		session.flush();
		return objeto;
	}

	public Funcionalidade incluir(Funcionalidade objeto) throws InfrastructureException, Exception{
		objeto = getFuncionalidadeDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Funcionalidade objeto) throws InfrastructureException, Exception{
		getFuncionalidadeDao().makeTransient(objeto);
		session.flush();
	}

	public Funcionalidade consultar(Long idFuncionalidade) throws InfrastructureException, Exception{
		return getFuncionalidadeDao().get(idFuncionalidade);
	}

}
