package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import qcs.base.modulo.Funcionalidade;
import qcs.base.modulo.persistence.dao.FuncionalidadeDao;
import qcs.base.modulo.persistence.dao.impl.FuncionalidadeDaoImpl;
import qcs.datamodel.HibernateListDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class ListaFuncionalidadeDataProvider extends HibernateListDataProvider<Funcionalidade>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private FuncionalidadeDao funcionalidadeDao;

	@Override
	public List<Funcionalidade> getAllItems() throws InfrastructureException, Exception{
		return (List<Funcionalidade>)getFuncionalidadeDao().listAll();
	}

	public Map<Long, String> getMapItems() throws InfrastructureException, Exception{
		if(mapItems == null){
			mapItems = getFuncionalidadeDao().listOfValues();
		}
		return mapItems;
	}

	public FuncionalidadeDao getFuncionalidadeDao() {
		if(funcionalidadeDao == null){
			this.funcionalidadeDao = new FuncionalidadeDaoImpl(session);
		}
		return funcionalidadeDao;
	}

	public void setFuncionalidadeDao(FuncionalidadeDao funcionalidadeDao) {
		this.funcionalidadeDao = funcionalidadeDao;
	}
}
