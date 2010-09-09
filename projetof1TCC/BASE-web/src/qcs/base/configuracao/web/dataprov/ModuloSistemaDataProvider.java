package qcs.base.configuracao.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.ModuloSistema;
import qcs.base.modulo.persistence.dao.ModuloSistemaDao;
import qcs.base.modulo.persistence.dao.impl.ModuloSistemaDaoImpl;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.ModuloSistemaView;

public class ModuloSistemaDataProvider extends HibernateDataProvider<ModuloSistemaView, ModuloSistema, Long> {
	private static final long serialVersionUID = 1L;
	private ModuloSistemaDao moduloSistemaDao;


	@Override
	public ModuloSistemaView getObjectByPk(Object idPk) {
		for (ModuloSistemaView documento : getAllItems()) {
			if (documento.getIdModulo().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("ModuloSistema pk=" + idPk.toString()
				+ " não encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (ModuloSistemaView moduloSistema : getAllItems()) {
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
	 * @return lista de objetos do moduloSistema
	 */
	public List<ModuloSistemaView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<ModuloSistemaView> lista = null;
		try{
			lista = (List<ModuloSistemaView>)getModuloSistemaDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

			//			log.debug(">>>> 10: "+lista.size());
			if(lista!=null){
				while(lista.size() < numberOfRows){
					//					log.debug(">>>> 11: "+lista.size());
					lista.add(classeObject.newInstance());
				}
			}

			//			for(ModuloSistema tipDoc : lista){
			//				log.debug(">>> lista "+tipDoc.getDescricao());
			//			}
			setAllItems(lista);
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ModuloSistemaDataProvider : getObjectByRange", e.getMessage()));
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
					new FacesMessage("Erro ModuloSistemaDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object> valores) {
		try{
			setSize(getModuloSistemaDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ModuloSistemaDataProvider : getRowCount", e.getMessage()));
			session.getTransaction().rollback();
		}
		return getSize();
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getModuloSistemaDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ModuloSistemaDataProvider : getRowCount", e.getMessage()));
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

	public ModuloSistemaDao getModuloSistemaDao() {
		this.moduloSistemaDao = new ModuloSistemaDaoImpl(session);
		return moduloSistemaDao;
	}

	public void setModuloSistemaDao(ModuloSistemaDao moduloSistemaDao) {
		this.moduloSistemaDao = moduloSistemaDao;
	}

	public ModuloSistema alterar(ModuloSistema objeto) throws InfrastructureException, Exception{
		objeto = getModuloSistemaDao().update(objeto);
		session.flush();		

		return objeto;
	}

	public ModuloSistema incluir(ModuloSistema objeto) throws InfrastructureException, Exception{
		objeto = getModuloSistemaDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(ModuloSistema objeto) throws InfrastructureException, Exception{
		getModuloSistemaDao().makeTransient(objeto);
		session.flush();
	}

	public ModuloSistema consultar(Long idModulo) throws InfrastructureException, Exception{
		return getModuloSistemaDao().find(idModulo);				
	}	


}
