package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusBrinquedoDao;
import qcs.base.modulo.persistence.dao.impl.StatusBrinquedoDaoImpl;
import qcs.base.negocio.StatusBrinquedo;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;

public class StatusBrinquedoDataProvider extends HibernateDataProvider<StatusBrinquedoView, StatusBrinquedo, Long> {
	private static final long serialVersionUID = 1L;
	private StatusBrinquedoDao statusBrinquedoDao;

	@Override
	public StatusBrinquedoView getObjectByPk(Object idPk) {
		for (StatusBrinquedoView documento : getAllItems()) {
			if (documento.getIdStatusBrinquedo().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("StatusBrinquedo pk=" + idPk.toString()
				+ " não encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getStatusBrinquedoDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusBrinquedoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (StatusBrinquedoView statusBrinquedo : getAllItems()) {
			if (statusBrinquedo.getIdStatusBrinquedo().equals(idPk)) {
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
	 * @return lista de objetos do statusBrinquedo
	 */
	public List<StatusBrinquedoView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<StatusBrinquedoView> lista = null;
		try{
			lista = (List<StatusBrinquedoView>)getStatusBrinquedoDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro StatusBrinquedoDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getStatusBrinquedoDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusBrinquedoDataProvider : getRowCount", e.getMessage()));
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
			setSize(getStatusBrinquedoDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusBrinquedoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public StatusBrinquedoDao getStatusBrinquedoDao() {
		this.statusBrinquedoDao = new StatusBrinquedoDaoImpl(session);
		return statusBrinquedoDao;
	}

	public void setStatusBrinquedoDao(StatusBrinquedoDao statusBrinquedoDao) {
		this.statusBrinquedoDao = statusBrinquedoDao;
	}

	public StatusBrinquedo alterar(StatusBrinquedo objeto) throws InfrastructureException, Exception{
		objeto = getStatusBrinquedoDao().update(objeto);
		session.flush();
		return objeto;
	}

	public StatusBrinquedo incluir(StatusBrinquedo objeto) throws InfrastructureException, Exception{
		objeto = getStatusBrinquedoDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(StatusBrinquedo objeto) throws InfrastructureException, Exception{
		getStatusBrinquedoDao().makeTransient(objeto);
		session.flush();
	}

	public StatusBrinquedo consultar(Long idStatusBrinquedo) throws InfrastructureException, Exception{
		return getStatusBrinquedoDao().get(idStatusBrinquedo);
	}

}
