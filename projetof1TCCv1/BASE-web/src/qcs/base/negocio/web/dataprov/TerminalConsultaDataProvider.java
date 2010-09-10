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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
