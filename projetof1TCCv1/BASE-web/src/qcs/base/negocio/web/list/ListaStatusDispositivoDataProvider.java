package qcs.base.negocio.web.list;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusDispositivoDao;
import qcs.base.modulo.persistence.dao.impl.StatusDispositivoDaoImpl;
import qcs.base.negocio.StatusDispositivo;
import qcs.datamodel.HibernateListDataProvider;

public class ListaStatusDispositivoDataProvider extends HibernateListDataProvider<StatusDispositivo>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private StatusDispositivoDao statusDispositivoDao;

	@Override
	public List<StatusDispositivo> getAllItems() {
		try{
			return (List<StatusDispositivo>)getStatusDispositivoDao().listAll();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusDispositivos", e.getMessage()));
		}
		return null;
	}

	public Map<Long, String> getMapItems(){
		try{
			if(mapItems == null){
				mapItems = getStatusDispositivoDao().listOfValues();
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusDispositivos", e.getMessage()));
		}
		return mapItems;
	}

	public StatusDispositivoDao getStatusDispositivoDao() {
		if(statusDispositivoDao == null){
			statusDispositivoDao = new StatusDispositivoDaoImpl(session);
		}
		return statusDispositivoDao;
	}

	public void setStatusDispositivoDao(StatusDispositivoDao statusDispositivoDao) {
		this.statusDispositivoDao = statusDispositivoDao;
	}
}
