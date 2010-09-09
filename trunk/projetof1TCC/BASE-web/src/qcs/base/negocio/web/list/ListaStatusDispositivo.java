package qcs.base.negocio.web.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

public class ListaStatusDispositivo implements Serializable{
	private static final long serialVersionUID = 1L;
	private ListaStatusDispositivoDataProvider listaStatusDispositivoDataProvider;

	private Map<Long, String> mapStatusDispositivo;
	private List<SelectItem> selectItemsStatusDispositivo;
	
	public List<SelectItem> getSelectItemsStatusDispositivo() {
		if(selectItemsStatusDispositivo == null){
			selectItemsStatusDispositivo = new ArrayList<SelectItem>();
			for(Long key : getMapStatusDispositivo().keySet()){
				selectItemsStatusDispositivo.add(new SelectItem(key, getMapStatusDispositivo().get(key), getMapStatusDispositivo().get(key)));
			}
		}
		return selectItemsStatusDispositivo;
	}

	public void setSelectItemsStatusDispositivo(List<SelectItem> selectItemsStatusDispositivo) {
		this.selectItemsStatusDispositivo = selectItemsStatusDispositivo;
	}

	public Map<Long, String> getMapStatusDispositivo() {
		if(mapStatusDispositivo == null){
			mapStatusDispositivo = getListaStatusDispositivoDataProvider().getMapItems();
		}
		return mapStatusDispositivo;
	}

	public void setMapStatusDispositivo(
			HashMap<Long, String> mapStatusDispositivo) {
		this.mapStatusDispositivo = mapStatusDispositivo;
	}
	
	public ListaStatusDispositivoDataProvider getListaStatusDispositivoDataProvider() {
		return listaStatusDispositivoDataProvider;
	}

	public void setListaStatusDispositivoDataProvider(
			ListaStatusDispositivoDataProvider listaStatusDispositivoDataProvider) {
		this.listaStatusDispositivoDataProvider = listaStatusDispositivoDataProvider;
	}

	public void setMapStatusDispositivo(Map<Long, String> mapStatusDispositivo) {
		this.mapStatusDispositivo = mapStatusDispositivo;
	}
}
