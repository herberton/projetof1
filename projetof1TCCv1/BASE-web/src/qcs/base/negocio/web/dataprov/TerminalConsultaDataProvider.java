package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.TerminalConsultaDao;
import qcs.base.modulo.persistence.dao.impl.TerminalConsultaDaoImpl;
import qcs.base.negocio.TerminalConsulta;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.TerminalConsultaView;

public class TerminalConsultaDataProvider extends HibernateDataProvider<TerminalConsultaView, TerminalConsulta, Long> {
	private static final long serialVersionUID = 1L;
	private TerminalConsultaDao terminalConsultaDao;

	@Override
	public TerminalConsultaView getObjectByPk(Object idPk) {
		for (TerminalConsultaView documento : getAllItems()) {
			if (documento.getIdTerminalConsulta().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("TerminalConsulta pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getTerminalConsultaDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TerminalConsultaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (TerminalConsultaView terminalConsulta : getAllItems()) {
			if (terminalConsulta.getIdTerminalConsulta().equals(idPk)) {
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
	 * @return lista de objetos do terminalConsulta
	 */
	public List<TerminalConsultaView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<TerminalConsultaView> lista = null;
		try{
			lista = (List<TerminalConsultaView>)getTerminalConsultaDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro TerminalConsultaDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getTerminalConsultaDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TerminalConsultaDataProvider : getRowCount", e.getMessage()));
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
			setSize(getTerminalConsultaDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro TerminalConsultaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public TerminalConsultaDao getTerminalConsultaDao() {
		this.terminalConsultaDao = new TerminalConsultaDaoImpl(session);
		return terminalConsultaDao;
	}

	public void setTerminalConsultaDao(TerminalConsultaDao terminalConsultaDao) {
		this.terminalConsultaDao = terminalConsultaDao;
	}

	public TerminalConsulta alterar(TerminalConsulta objeto) throws InfrastructureException, Exception{
		objeto = getTerminalConsultaDao().update(objeto);
		session.flush();
		return objeto;
	}

	public TerminalConsulta incluir(TerminalConsulta objeto) throws InfrastructureException, Exception{
		objeto = getTerminalConsultaDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(TerminalConsulta objeto) throws InfrastructureException, Exception{
		getTerminalConsultaDao().makeTransient(objeto);
		session.flush();
	}

	public TerminalConsulta consultar(Long idTerminalConsulta) throws InfrastructureException, Exception{
		return getTerminalConsultaDao().get(idTerminalConsulta);
	}

}
