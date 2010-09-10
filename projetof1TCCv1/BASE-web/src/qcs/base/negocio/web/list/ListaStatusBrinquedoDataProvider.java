package qcs.base.negocio.web.list;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusBrinquedoDao;
import qcs.base.modulo.persistence.dao.impl.StatusBrinquedoDaoImpl;
import qcs.base.negocio.StatusBrinquedo;
import qcs.datamodel.HibernateListDataProvider;

public class ListaStatusBrinquedoDataProvider extends HibernateListDataProvider<StatusBrinquedo>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private StatusBrinquedoDao statusBrinquedoDao;

	@Override
	public List<StatusBrinquedo> getAllItems() {
		try{
			return (List<StatusBrinquedo>)getStatusBrinquedoDao().listAll();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusBrinquedos", e.getMessage()));
		}
		return null;
	}

	public Map<Long, String> getMapItems(){
		try{
			if(mapItems == null){
				mapItems = getStatusBrinquedoDao().listOfValues();
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusBrinquedos", e.getMessage()));
		}
		return mapItems;
	}

	public StatusBrinquedoDao getStatusBrinquedoDao() {
		if(statusBrinquedoDao == null){
			statusBrinquedoDao = new StatusBrinquedoDaoImpl(session);
		}
		return statusBrinquedoDao;
	}

	public void setStatusBrinquedoDao(StatusBrinquedoDao statusBrinquedoDao) {
		this.statusBrinquedoDao = statusBrinquedoDao;
	}
}
