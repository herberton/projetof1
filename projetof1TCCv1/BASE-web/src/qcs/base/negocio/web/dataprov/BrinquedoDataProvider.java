package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.BrinquedoDao;
import qcs.base.modulo.persistence.dao.impl.BrinquedoDaoImpl;
import qcs.base.negocio.Brinquedo;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.BrinquedoView;

public class BrinquedoDataProvider extends HibernateDataProvider<BrinquedoView, Brinquedo, Long> {
	private static final long serialVersionUID = 1L;
	private BrinquedoDao brinquedoDao;

	@Override
	public BrinquedoView getObjectByPk(Object idPk) {
		for (BrinquedoView documento : getAllItems()) {
			if (documento.getIdBrinquedo().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Brinquedo pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getBrinquedoDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BrinquedoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (BrinquedoView brinquedo : getAllItems()) {
			if (brinquedo.getIdBrinquedo().equals(idPk)) {
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
	 * @return lista de objetos do brinquedo
	 */
	public List<BrinquedoView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<BrinquedoView> lista = null;
		try{
			lista = (List<BrinquedoView>)getBrinquedoDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro BrinquedoDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getBrinquedoDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BrinquedoDataProvider : getRowCount", e.getMessage()));
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
			setSize(getBrinquedoDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro BrinquedoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public BrinquedoDao getBrinquedoDao() {
		this.brinquedoDao = new BrinquedoDaoImpl(session);
		return brinquedoDao;
	}

	public void setBrinquedoDao(BrinquedoDao brinquedoDao) {
		this.brinquedoDao = brinquedoDao;
	}

	public Brinquedo alterar(Brinquedo objeto) throws InfrastructureException, Exception{
		objeto = getBrinquedoDao().update(objeto);
		session.flush();
		return objeto;
	}

	public Brinquedo incluir(Brinquedo objeto) throws InfrastructureException, Exception{
		objeto = getBrinquedoDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Brinquedo objeto) throws InfrastructureException, Exception{
		getBrinquedoDao().makeTransient(objeto);
		session.flush();
	}

	public Brinquedo consultar(Long idBrinquedo) throws InfrastructureException, Exception{
		return getBrinquedoDao().get(idBrinquedo);
	}

}
