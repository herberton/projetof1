package qcs.base.negocio.web.list;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.persistence.dao.StatusClienteDao;
import qcs.base.modulo.persistence.dao.impl.StatusClienteDaoImpl;
import qcs.base.negocio.StatusCliente;
import qcs.datamodel.HibernateListDataProvider;

public class ListaStatusClienteDataProvider extends HibernateListDataProvider<StatusCliente>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private StatusClienteDao statusClienteDao;

	@Override
	public List<StatusCliente> getAllItems() {
		try{
			return (List<StatusCliente>)getStatusClienteDao().listAll();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusClientes", e.getMessage()));
		}
		return null;
	}

	public Map<Long, String> getMapItems(){
		try{
			if(mapItems == null){
				mapItems = getStatusClienteDao().listOfValues();
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de statusClientes", e.getMessage()));
		}
		return mapItems;
	}

	public StatusClienteDao getStatusClienteDao() {
		if(statusClienteDao == null){
			statusClienteDao = new StatusClienteDaoImpl(session);
		}
		return statusClienteDao;
	}

	public void setStatusClienteDao(StatusClienteDao statusClienteDao) {
		this.statusClienteDao = statusClienteDao;
	}
}
