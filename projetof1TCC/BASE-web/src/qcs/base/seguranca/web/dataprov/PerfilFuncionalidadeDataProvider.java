package qcs.base.seguranca.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.seguranca.PerfilFuncionalidade;
import qcs.base.seguranca.persistence.dao.PerfilFuncionalidadeDao;
import qcs.base.seguranca.persistence.dao.impl.PerfilFuncionalidadeDaoImpl;
import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class PerfilFuncionalidadeDataProvider extends HibernateDataProvider<PerfilFuncionalidadeView, PerfilFuncionalidade, Long> {
	private static final long serialVersionUID = 1L;
	private PerfilFuncionalidadeDao perfilFuncionalidadeDao;

	@Override
	public PerfilFuncionalidadeView getObjectByPk(Object idPk) {
		for (PerfilFuncionalidadeView documento : getAllItems()) {
			if (documento.getIdPerfilFunc().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("PerfilFuncionalidade pk=" + idPk.toString()
				+ " não encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getPerfilFuncionalidadeDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilFuncionalidadeDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (PerfilFuncionalidadeView perfilFuncionalidade : getAllItems()) {
			if (perfilFuncionalidade.getIdPerfilFunc().equals(idPk)) {
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
	 * @return lista de objetos do perfilFuncionalidade
	 */
	public List<PerfilFuncionalidadeView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<PerfilFuncionalidadeView> lista = null;
		try{
			lista = (List<PerfilFuncionalidadeView>)getPerfilFuncionalidadeDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro PerfilFuncionalidadeDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getPerfilFuncionalidadeDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilFuncionalidadeDataProvider : getRowCount", e.getMessage()));
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
			setSize(getPerfilFuncionalidadeDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilFuncionalidadeDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public PerfilFuncionalidadeDao getPerfilFuncionalidadeDao() {
		this.perfilFuncionalidadeDao = new PerfilFuncionalidadeDaoImpl(session);
		return perfilFuncionalidadeDao;
	}

	public void setPerfilFuncionalidadeDao(PerfilFuncionalidadeDao perfilFuncionalidadeDao) {
		this.perfilFuncionalidadeDao = perfilFuncionalidadeDao;
	}

	public PerfilFuncionalidade alterar(PerfilFuncionalidade objeto) throws InfrastructureException, Exception{
		objeto = getPerfilFuncionalidadeDao().update(objeto);
		session.flush();
		return objeto;
	}

	public PerfilFuncionalidade incluir(PerfilFuncionalidade objeto) throws InfrastructureException, Exception{
		objeto = getPerfilFuncionalidadeDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(PerfilFuncionalidade objeto) throws InfrastructureException, Exception{
		getPerfilFuncionalidadeDao().makeTransient(objeto);
		session.flush();
	}

	public PerfilFuncionalidade consultar(Long idPerfilFuncionalidade) throws InfrastructureException, Exception{
		return getPerfilFuncionalidadeDao().get(idPerfilFuncionalidade);
	}

}
