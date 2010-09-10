package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.dao.ValorTabAuxDao;
import qcs.base.tabaux.persistence.dao.impl.ValorTabAuxDaoImpl;
import qcs.datamodel.HibernateListDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class ListaValorTabAuxDataProvider extends HibernateListDataProvider<ValorTabAux>{
	private static final long serialVersionUID = 1L;
	private ValorTabAuxDao valorTabAuxDao;

	private Map<Long, String> mapItemsUf;
	private Map<Long, String> mapItemsTipoLog;
	private Map<Long, String> mapItemsTipoPessoa;

	@Override
	public List<ValorTabAux> getAllItems()throws InfrastructureException, Exception{
		return (List<ValorTabAux>)getValorTabAuxDao().listAll();
	}

	public ValorTabAuxDao getValorTabAuxDao() {
		if(valorTabAuxDao == null){
			this.valorTabAuxDao = new ValorTabAuxDaoImpl(session);
		}
		return valorTabAuxDao;
	}

	public void setValorTabAuxDao(ValorTabAuxDao valorTabAuxDao) {
		this.valorTabAuxDao = valorTabAuxDao;
	}

	public Map<Long, String> getMapItemsUf()throws InfrastructureException, Exception{
		if(mapItemsUf == null){
			mapItemsUf = getValorTabAuxDao().listOfValuesUf();
		}
		return mapItemsUf;
	}

	public Map<Long, String> getMapItemsTipoLog() throws InfrastructureException, Exception {
		if(mapItemsTipoLog == null){
			mapItemsTipoLog = getValorTabAuxDao().listOfValuesTipoLog();
		}
		return mapItemsTipoLog;
	}
	
	public Map<Long, String> getMapItemsTipoPessoa() throws InfrastructureException, Exception {
		if(mapItemsTipoPessoa == null){
			mapItemsTipoPessoa = getValorTabAuxDao().listOfValuesTipoPessoa();
		}
		return mapItemsTipoPessoa;
	}
}
