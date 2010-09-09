package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.CatracaDao;
import qcs.base.modulo.persistence.dao.impl.CatracaDaoImpl;
import qcs.base.negocio.Catraca;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.CatracaView;

public class CatracaDataProvider extends HibernateDataProvider<CatracaView, Catraca, Long> {
	private static final long serialVersionUID = 1L;
	private CatracaDao catracaDao;

	@Override
	public CatracaView getObjectByPk(Object idPk) {
		for (CatracaView documento : getAllItems()) {
			if (documento.getIdCatraca().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Catraca pk=" + idPk.toString()
				+ " não encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getCatracaDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CatracaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (CatracaView catraca : getAllItems()) {
			if (catraca.getIdCatraca().equals(idPk)) {
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
	 * @return lista de objetos do catraca
	 */
	public List<CatracaView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<CatracaView> lista = null;
		try{
			lista = (List<CatracaView>)getCatracaDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro CatracaDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getCatracaDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CatracaDataProvider : getRowCount", e.getMessage()));
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
			setSize(getCatracaDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro CatracaDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public CatracaDao getCatracaDao() {
		this.catracaDao = new CatracaDaoImpl(session);
		return catracaDao;
	}

	public void setCatracaDao(CatracaDao catracaDao) {
		this.catracaDao = catracaDao;
	}

	public Catraca alterar(Catraca objeto) throws InfrastructureException, Exception{
		objeto = getCatracaDao().update(objeto);
		session.flush();
		return objeto;
	}

	public Catraca incluir(Catraca objeto) throws InfrastructureException, Exception{
		objeto = getCatracaDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Catraca objeto) throws InfrastructureException, Exception{
		getCatracaDao().makeTransient(objeto);
		session.flush();
	}

	public Catraca consultar(Long idCatraca) throws InfrastructureException, Exception{
		return getCatracaDao().get(idCatraca);
	}

}
