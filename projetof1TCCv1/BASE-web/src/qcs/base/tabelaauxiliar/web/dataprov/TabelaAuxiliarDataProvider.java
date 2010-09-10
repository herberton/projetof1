package qcs.base.tabelaauxiliar.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.persistence.dao.TabelaAuxiliarDao;
import qcs.base.tabaux.persistence.dao.impl.TabelaAuxiliarDaoImpl;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class TabelaAuxiliarDataProvider extends HibernateDataProvider<TabelaAuxiliarView, TabelaAuxiliar, Long> {
	private static final long serialVersionUID = 1L;
	private TabelaAuxiliarDao tabelaAuxiliarDao;

	@Override
	public TabelaAuxiliarView getObjectByPk(Object idPk) {
		for (TabelaAuxiliarView documento : getAllItems()) {
			if (documento.getIdTabAux().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("TabelaAuxiliar pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (TabelaAuxiliarView tabelaAuxiliar : getAllItems()) {
			if (tabelaAuxiliar.getIdTabAux().equals(idPk)) {
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
	 * @return lista de objetos do tabelaAuxiliar
	 */
	public List<TabelaAuxiliarView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<TabelaAuxiliarView> lista = null;
		try{
			lista = (List<TabelaAuxiliarView>)getTabelaAuxiliarDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro TabelaAuxiliarDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getTabelaAuxiliarDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TabelaAuxiliarDataProvider : getRowCount", e.getMessage()));
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
			setSize(getTabelaAuxiliarDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TabelaAuxiliarDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getTabelaAuxiliarDao().getMaxRows(atributosFiltros));
			return getSize();
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TabelaAuxiliarDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public TabelaAuxiliarDao getTabelaAuxiliarDao() {
		this.tabelaAuxiliarDao = new TabelaAuxiliarDaoImpl(session);
		return tabelaAuxiliarDao;
	}

	public TabelaAuxiliar consultar(Long tabelaAuxiliar) throws InfrastructureException, Exception{
		return getTabelaAuxiliarDao().get(tabelaAuxiliar);
	}

	public void setTabelaAuxiliarDao(TabelaAuxiliarDao tabelaAuxiliarDao) {
		this.tabelaAuxiliarDao = tabelaAuxiliarDao;
	}

	public TabelaAuxiliar alterar(TabelaAuxiliar objeto)throws InfrastructureException, Exception{
		objeto = getTabelaAuxiliarDao().update(objeto);
		session.flush();
	

		return objeto;
	}

	public TabelaAuxiliar incluir(TabelaAuxiliar objeto)throws InfrastructureException, Exception{
		objeto = getTabelaAuxiliarDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(TabelaAuxiliar objeto)throws InfrastructureException, Exception{
		getTabelaAuxiliarDao().makeTransient(objeto);
		session.flush();
	}

}
