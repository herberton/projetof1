package qcs.base.lov.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import qcs.base.modulo.ModuloSistema;
import qcs.base.modulo.persistence.dao.ModuloSistemaDao;
import qcs.base.modulo.persistence.dao.impl.ModuloSistemaDaoImpl;
import qcs.datamodel.HibernateListDataProvider;

public class ListaModuloSistemaDataProvider extends HibernateListDataProvider<ModuloSistema>{
	private static final long serialVersionUID = 1L;
	private Map<Long, String> mapItems;
	private ModuloSistemaDao moduloSistemaDao;

	@Override
	public List<ModuloSistema> getAllItems() {
		try{
			return (List<ModuloSistema>)getModuloSistemaDao().listAll();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de modulos sistema", e.getMessage()));
		}
		return null;
	}

	public Map<Long, String> getMapItems(){
		try{
			if(mapItems == null){
				mapItems = getModuloSistemaDao().listOfValues();
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar lista de modulos sistema", e.getMessage()));
		}
		return mapItems;
	}

	public ModuloSistemaDao getModuloSistemaDao() {
		if(moduloSistemaDao == null){
			this.moduloSistemaDao = new ModuloSistemaDaoImpl(session);
		}
		return moduloSistemaDao;
	}

	public void setModuloSistemaDao(ModuloSistemaDao moduloSistemaDao) {
		this.moduloSistemaDao = moduloSistemaDao;
	}
}
