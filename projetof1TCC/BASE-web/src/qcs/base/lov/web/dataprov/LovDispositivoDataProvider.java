package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.modulo.persistence.dao.DispositivoDao;
import qcs.base.modulo.persistence.dao.impl.DispositivoDaoImpl;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;
import qcs.persistence.rhdefensoria.view.LovDispositivoView;

public class LovDispositivoDataProvider extends HibernateDataProvider<LovDispositivoView, LovDispositivoView, Long> {
	protected static Log log = LogFactory.getLog(LovDispositivoDataProvider.class);
	private static final long serialVersionUID = 1L;
	private DispositivoDao DispositivoDao;

	@Override
	public LovDispositivoView getObjectByPk(Object idPk) {
		for (LovDispositivoView lovDispositivoVIew : getAllItems()) {
			if (lovDispositivoVIew.getIdDispositivo().equals(idPk)) {
				return lovDispositivoVIew;
			}
		}
		throw new RuntimeException("Dispositivo pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (LovDispositivoView lovDispositivoVIew : getAllItems()) {
			if (lovDispositivoVIew.getIdDispositivo().equals(idPk)) {
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
	 * @return lista de objetos do banco
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public List<LovDispositivoView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) throws InfrastructureException, Exception {
		log.debug("LovDispositivoDataProvider : getObjectByRange");
		List<LovDispositivoView> lista = null;
		lista = (List<LovDispositivoView>)getDispositivoDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

		if(lista!=null){
			while(lista.size() < numberOfRows){
				lista.add(classeObject.newInstance());
			}
		}
		setAllItems(lista);
		return lista;
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * ser� necessario sobrecarregar o m�todo.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 * @throws Exception 
	 * @throws InfrastructureException 
	 */
	public int getRowCount() throws InfrastructureException, Exception {
		setSize(getDispositivoDao().getMaxRows());
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
	 * @throws Exception 
	 * @throws InfrastructureException 
	 */	
	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object>valores) throws InfrastructureException, Exception {
		setSize(getDispositivoDao().getMaxRows(atributosFiltros, valores));
		return getSize();
	}

	public int getRowCount(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception {
		setSize(getDispositivoDao().getMaxRows(atributosFiltros));	
		return getSize();
	}	

	public DispositivoDao getDispositivoDao() {
		if(DispositivoDao == null){
			DispositivoDao = new DispositivoDaoImpl(session);
		}
		return DispositivoDao;
	}

	public void setDispositivoDao(DispositivoDao DispositivoDao) {
		this.DispositivoDao = DispositivoDao;
	}
}
