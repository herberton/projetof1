package qcs.base.negocio.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.DispositivoDao;
import qcs.base.modulo.persistence.dao.impl.DispositivoDaoImpl;
import qcs.base.negocio.Dispositivo;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.DispositivoView;

public class DispositivoDataProvider extends HibernateDataProvider<DispositivoView, Dispositivo, Long> {
	private static final long serialVersionUID = 1L;
	private DispositivoDao dispositivoDao;

	@Override
	public DispositivoView getObjectByPk(Object idPk) {
		for (DispositivoView documento : getAllItems()) {
			if (documento.getIdDispositivo().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Dispositivo pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	public int getRowCount(Map<String, Object> atributosFiltros) {
		try{
			setSize(getDispositivoDao().getMaxRows(atributosFiltros));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro DispositivoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (DispositivoView dispositivo : getAllItems()) {
			if (dispositivo.getIdDispositivo().equals(idPk)) {
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
	 * @return lista de objetos do dispositivo
	 */
	public List<DispositivoView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<DispositivoView> lista = null;
		try{
			lista = (List<DispositivoView>)getDispositivoDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro DispositivoDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getDispositivoDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro DispositivoDataProvider : getRowCount", e.getMessage()));
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
			setSize(getDispositivoDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro DispositivoDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public DispositivoDao getDispositivoDao() {
		this.dispositivoDao = new DispositivoDaoImpl(session);
		return dispositivoDao;
	}

	public void setDispositivoDao(DispositivoDao dispositivoDao) {
		this.dispositivoDao = dispositivoDao;
	}

	public Dispositivo alterar(Dispositivo objeto) throws InfrastructureException, Exception{
		objeto = getDispositivoDao().update(objeto);
		session.flush();
		return objeto;
	}

	public Dispositivo incluir(Dispositivo objeto) throws InfrastructureException, Exception{
		objeto = getDispositivoDao().add(objeto);
		session.flush();

		return objeto;
	}

	public void excluir(Dispositivo objeto) throws InfrastructureException, Exception{
		getDispositivoDao().makeTransient(objeto);
		session.flush();
	}

	public Dispositivo consultar(Long idDispositivo) throws InfrastructureException, Exception{
		return getDispositivoDao().get(idDispositivo);
	}

	public Dispositivo validaDispositivo(String cod_dispositivo){

			return getDispositivoDao().validaDispositivo(cod_dispositivo);

	}

}
