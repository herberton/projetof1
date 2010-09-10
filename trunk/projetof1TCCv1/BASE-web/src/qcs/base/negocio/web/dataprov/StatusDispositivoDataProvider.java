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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
