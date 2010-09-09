package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusDispositivoDao;
import qcs.base.modulo.persistence.dao.impl.StatusDispositivoDaoImpl;
import qcs.base.negocio.StatusDispositivo;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.StatusDispositivoView;

public class StatusDispositivoDataProvider extends HibernateDataProvider<StatusDispositivoView, StatusDispositivo, Long> {
	private static final long serialVersionUID = 1L;
	private StatusDispositivoDao statusDispositivoDao;

	@Override
	public StatusDispositivoView getObjectByPk(Object idPk) {
		for (StatusDispositivoView documento : getAllItems()) {
			if (documento.getIdStatusDispositivo().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("StatusDispositivo pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getStatusDispositivoDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusDispositivoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (StatusDispositivoView statusDispositivo : getAllItems()) {
			if (statusDispositivo.getIdStatusDispositivo().equals(idPk)) {
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
	 * @return lista de objetos do statusDispositivo
	 */
	public List<StatusDispositivoView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<StatusDispositivoView> lista = null;
		try{
			lista = (List<StatusDispositivoView>)getStatusDispositivoDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro StatusDispositivoDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getStatusDispositivoDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusDispositivoDataProvider : getRowCount", e.getMessage()));
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
			setSize(getStatusDispositivoDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusDispositivoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public StatusDispositivoDao getStatusDispositivoDao() {
		this.statusDispositivoDao = new StatusDispositivoDaoImpl(session);
		return statusDispositivoDao;
	}

	public void setStatusDispositivoDao(StatusDispositivoDao statusDispositivoDao) {
		this.statusDispositivoDao = statusDispositivoDao;
	}

	public StatusDispositivo alterar(StatusDispositivo objeto) throws InfrastructureException, Exception{
		objeto = getStatusDispositivoDao().update(objeto);
		session.flush();
		return objeto;
	}

	public StatusDispositivo incluir(StatusDispositivo objeto) throws InfrastructureException, Exception{
		objeto = getStatusDispositivoDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(StatusDispositivo objeto) throws InfrastructureException, Exception{
		getStatusDispositivoDao().makeTransient(objeto);
		session.flush();
	}

	public StatusDispositivo consultar(Long idStatusDispositivo) throws InfrastructureException, Exception{
		return getStatusDispositivoDao().get(idStatusDispositivo);
	}

}
