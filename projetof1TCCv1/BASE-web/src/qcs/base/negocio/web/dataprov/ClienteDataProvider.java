package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.ClienteDao;
import qcs.base.modulo.persistence.dao.impl.ClienteDaoImpl;
import qcs.base.negocio.Cliente;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.ClienteView;

public class ClienteDataProvider extends HibernateDataProvider<ClienteView, Cliente, Long> {
	private static final long serialVersionUID = 1L;
	private ClienteDao clienteDao;

	@Override
	public ClienteView getObjectByPk(Object idPk) {
		for (ClienteView documento : getAllItems()) {
			if (documento.getIdCliente().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Cliente pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getClienteDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (ClienteView cliente : getAllItems()) {
			if (cliente.getIdCliente().equals(idPk)) {
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
	 * @return lista de objetos do cliente
	 */
	public List<ClienteView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<ClienteView> lista = null;
		try{
			lista = (List<ClienteView>)getClienteDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro ClienteDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getClienteDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ClienteDataProvider : getRowCount", e.getMessage()));
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
			setSize(getClienteDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public ClienteDao getClienteDao() {
		this.clienteDao = new ClienteDaoImpl(session);
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public Cliente alterar(Cliente objeto) throws InfrastructureException, Exception{
		objeto = getClienteDao().update(objeto);
		session.flush();
		return objeto;
	}

	public Cliente incluir(Cliente objeto) throws InfrastructureException, Exception{
		objeto = getClienteDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Cliente objeto) throws InfrastructureException, Exception{
		getClienteDao().makeTransient(objeto);
		session.flush();
	}

	public Cliente consultar(Long idCliente) throws InfrastructureException, Exception{
		return getClienteDao().get(idCliente);
	}

}
