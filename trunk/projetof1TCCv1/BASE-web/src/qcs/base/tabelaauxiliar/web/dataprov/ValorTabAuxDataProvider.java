package qcs.base.tabelaauxiliar.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.dao.ValorTabAuxDao;
import qcs.base.tabaux.persistence.dao.impl.ValorTabAuxDaoImpl;
import qcs.base.tabaux.persistence.view.ValorTabAuxView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class ValorTabAuxDataProvider extends HibernateDataProvider<ValorTabAuxView, ValorTabAux, Long> {
	protected static Log log = LogFactory.getLog(ValorTabAuxDataProvider.class);
	private static final long serialVersionUID = 1L;
	private ValorTabAuxDao valorTabAuxDao;


	public ValorTabAuxDataProvider(){	}

	@Override
	public ValorTabAuxView getObjectByPk(Object idPk) {
		for (ValorTabAuxView documento : getAllItems()) {
			if (documento.getIdValTabAux().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Tipo de processamento pk=" + idPk.toString()
				+ " não encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (ValorTabAuxView valorTabAux : getAllItems()) {
			if (valorTabAux.getIdValTabAux().equals(idPk)) {
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
	 * @return lista de objetos do valorTabAux
	 */
	public List<ValorTabAuxView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		log.debug("HibernateDataProvider : getObjectByRange");
		List<ValorTabAuxView> lista = null;
		try{
			lista = (List<ValorTabAuxView>)getValorTabAuxDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);
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
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ValorTabAuxDataProvider : getObjectByRange", e.getMessage()));
			session.getTransaction().rollback();
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
			setSize(getValorTabAuxDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ValorTabAuxDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
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
		setSize(getValorTabAuxDao().getMaxRows());
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ValorTabAuxDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}
	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
		setSize(getValorTabAuxDao().getMaxRows(atributosFiltros));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ValorTabAuxDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public ValorTabAux alterar(ValorTabAux valorTabAux) throws InfrastructureException, Exception{
		valorTabAux = getValorTabAuxDao().update(valorTabAux);
		session.flush();
		return valorTabAux;
	}

	public ValorTabAux incluir(ValorTabAux objeto) throws InfrastructureException, Exception{
		objeto = getValorTabAuxDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(ValorTabAux objeto) throws InfrastructureException, Exception{
		getValorTabAuxDao().makeTransient(objeto);
		session.flush();
	}	
	
	public ValorTabAux consultar(Long id) {
		try{
			 return getValorTabAuxDao().get(id);
		}catch(Exception e){
			session.getTransaction().rollback();
		}
		return null;
	}
	
	public ValorTabAuxDao getValorTabAuxDao() {
		this.valorTabAuxDao = new ValorTabAuxDaoImpl(session);
		return valorTabAuxDao;
	}

}