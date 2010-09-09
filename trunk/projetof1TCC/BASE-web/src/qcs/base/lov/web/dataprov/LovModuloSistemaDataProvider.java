package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.ModuloSistemaDao;
import qcs.base.modulo.persistence.dao.impl.ModuloSistemaDaoImpl;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.rhdefensoria.view.ListOfValuesModuloSistemaView;

public class LovModuloSistemaDataProvider extends HibernateDataProvider<ListOfValuesModuloSistemaView, ListOfValuesModuloSistemaView, Long> {
	private static final long serialVersionUID = 1L;
	private ModuloSistemaDao moduloSistemaDao;

	@Override
	public ListOfValuesModuloSistemaView getObjectByPk(Object idPk) {
		for (ListOfValuesModuloSistemaView moduloSistema : getAllItems()) {
			if (moduloSistema.getIdModulo().equals(idPk)) {
				return moduloSistema;
			}
		}
		throw new RuntimeException("ModuloSistema pk=" + idPk.toString()
				+ " não encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (ListOfValuesModuloSistemaView moduloSistema : getAllItems()) {
			if (moduloSistema.getIdModulo().equals(idPk)) {
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
	 * @return lista de objetos do banco
	 */
	public List<ListOfValuesModuloSistemaView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<ListOfValuesModuloSistemaView> lista = null;
		try{
			lista = (List<ListOfValuesModuloSistemaView>)getModuloSistemaDao().listOfValuesWithFilter(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro LovModuloSistemaDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getModuloSistemaDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovModuloSistemaDataProvider : getRowCount", e.getMessage()));
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
			setSize(getModuloSistemaDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovModuloSistemaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public ModuloSistemaDao getModuloSistemaDao() {
		if(moduloSistemaDao == null){
			moduloSistemaDao = new ModuloSistemaDaoImpl(session);
		}
		return moduloSistemaDao;
	}

	public void setModuloSistemaDao(ModuloSistemaDao moduloSistemaDao) {
		this.moduloSistemaDao = moduloSistemaDao;
	}
}
