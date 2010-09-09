package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusClienteDao;
import qcs.base.modulo.persistence.dao.impl.StatusClienteDaoImpl;
import qcs.base.negocio.StatusCliente;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.StatusClienteView;

public class StatusClienteDataProvider extends HibernateDataProvider<StatusClienteView, StatusCliente, Long> {
	private static final long serialVersionUID = 1L;
	private StatusClienteDao statusClienteDao;

	@Override
	public StatusClienteView getObjectByPk(Object idPk) {
		for (StatusClienteView documento : getAllItems()) {
			if (documento.getIdStatusCliente().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("StatusCliente pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getStatusClienteDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (StatusClienteView statusCliente : getAllItems()) {
			if (statusCliente.getIdStatusCliente().equals(idPk)) {
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
	 * @return lista de objetos do statusCliente
	 */
	public List<StatusClienteView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<StatusClienteView> lista = null;
		try{
			lista = (List<StatusClienteView>)getStatusClienteDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro StatusClienteDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getStatusClienteDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusClienteDataProvider : getRowCount", e.getMessage()));
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
			setSize(getStatusClienteDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro StatusClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public StatusClienteDao getStatusClienteDao() {
		this.statusClienteDao = new StatusClienteDaoImpl(session);
		return statusClienteDao;
	}

	public void setStatusClienteDao(StatusClienteDao statusClienteDao) {
		this.statusClienteDao = statusClienteDao;
	}

	public StatusCliente alterar(StatusCliente objeto) throws InfrastructureException, Exception{
		objeto = getStatusClienteDao().update(objeto);
		session.flush();
		return objeto;
	}

	public StatusCliente incluir(StatusCliente objeto) throws InfrastructureException, Exception{
		objeto = getStatusClienteDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(StatusCliente objeto) throws InfrastructureException, Exception{
		getStatusClienteDao().makeTransient(objeto);
		session.flush();
	}

	public StatusCliente consultar(Long idStatusCliente) throws InfrastructureException, Exception{
		return getStatusClienteDao().get(idStatusCliente);
	}

}
