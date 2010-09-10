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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade utilizando os filtros.
	 * @param atributosFiltros - são os filtros que forem preenchidos pelo usuário na tela de pesquisa:
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é a expressão de comparação
	 * @param valores são os valores que deverão ser comparados
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é o valor que deve ser comparado
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
