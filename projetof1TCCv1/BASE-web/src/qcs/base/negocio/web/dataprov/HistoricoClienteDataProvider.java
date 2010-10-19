package qcs.base.negocio.web.dataprov;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.HistoricoClienteDao;
import qcs.base.modulo.persistence.dao.impl.HistoricoClienteDaoImpl;
import qcs.base.negocio.Cliente;
import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.HistoricoCliente;
import qcs.base.negocio.StatusDispositivo;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.HistoricoClienteView;

public class HistoricoClienteDataProvider extends HibernateDataProvider<HistoricoClienteView, HistoricoCliente, Long> {
	private static final long serialVersionUID = 1L;
	private HistoricoClienteDao historicoClienteDao;

	@Override
	public HistoricoClienteView getObjectByPk(Object idPk) {
		for (HistoricoClienteView documento : getAllItems()) {
			if (documento.getId_historico_cliente().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("HistoricoCliente pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getHistoricoClienteDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro HistoricoClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (HistoricoClienteView historicoCliente : getAllItems()) {
			if (historicoCliente.getId_historico_cliente().equals(idPk)) {
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
	 * @return lista de objetos do historicoCliente
	 */
	public List<HistoricoClienteView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<HistoricoClienteView> lista = null;
		try{
			lista = (List<HistoricoClienteView>)getHistoricoClienteDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro HistoricoClienteDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getHistoricoClienteDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro HistoricoClienteDataProvider : getRowCount", e.getMessage()));
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
			setSize(getHistoricoClienteDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro HistoricoClienteDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public HistoricoClienteDao getHistoricoClienteDao() {
		this.historicoClienteDao = new HistoricoClienteDaoImpl(session);
		return historicoClienteDao;
	}

	public void setHistoricoClienteDao(HistoricoClienteDao historicoClienteDao) {
		this.historicoClienteDao = historicoClienteDao;
	}

	public HistoricoCliente alterar(HistoricoCliente objeto) throws InfrastructureException, Exception{
		objeto = getHistoricoClienteDao().update(objeto);
		session.flush();
		return objeto;
	}

	public HistoricoCliente incluir(HistoricoCliente objeto) throws InfrastructureException, Exception{
		objeto = getHistoricoClienteDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(HistoricoCliente objeto) throws InfrastructureException, Exception{
		getHistoricoClienteDao().makeTransient(objeto);
		session.flush();
	}

	public HistoricoCliente consultar(Long idHistoricoCliente) throws InfrastructureException, Exception{
		return getHistoricoClienteDao().get(idHistoricoCliente);
	}

	public void insereHistoricoClienteEntradaParque(Cliente cliente,Dispositivo dispositivo,StatusDispositivo statusDispositivo){

		HistoricoCliente historicoCliente = new HistoricoCliente();
		historicoCliente.setCliente(cliente);
		historicoCliente.setDispositivo(dispositivo);
		historicoCliente.setStatusDispositivo(statusDispositivo);
		historicoCliente.setDataHoraEntradaParque(new Date());

		try {
			incluir(historicoCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insereHistoricoClienteSaidaParque(Cliente cliente){
		
		HistoricoCliente historicoCliente = getHistoricoClienteDao().retornaHistoricoCliente(cliente.getIdCliente());
		
		historicoCliente.setDataHoraSaidaParque(new Date());
		
		try {
			alterar(historicoCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
