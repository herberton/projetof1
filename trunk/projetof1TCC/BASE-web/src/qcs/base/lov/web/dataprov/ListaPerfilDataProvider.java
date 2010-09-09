package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.dao.PerfilDao;
import qcs.base.seguranca.persistence.dao.impl.PerfilDaoImpl;
import qcs.datamodel.HibernateListDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class ListaPerfilDataProvider extends HibernateListDataProvider<Perfil>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private PerfilDao perfilDao;

	@Override
	public List<Perfil> getAllItems() throws InfrastructureException, Exception{
		return (List<Perfil>)getPerfilDao().listAll();
	}

	public Map<Long, String> getMapItems() throws InfrastructureException, Exception{
		if(mapItems == null){
			mapItems = getPerfilDao().listOfValues();
		}
		return mapItems;
	}

	public PerfilDao getPerfilDao() {
		if(perfilDao == null){
			this.perfilDao = new PerfilDaoImpl(session);
		}
		return perfilDao;
	}

	public void setPerfilDao(PerfilDao perfilDao) {
		this.perfilDao = perfilDao;
	}
}
