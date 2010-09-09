package qcs.base.negocio.web.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

public class ListaStatusBrinquedo implements Serializable{
	private static final long serialVersionUID = 1L;
	private ListaStatusBrinquedoDataProvider listaStatusBrinquedoDataProvider;

	private Map<Long, String> mapStatusBrinquedo;
	private List<SelectItem> selectItemsStatusBrinquedo;
	
	public List<SelectItem> getSelectItemsStatusBrinquedo() {
		if(selectItemsStatusBrinquedo == null){
			selectItemsStatusBrinquedo = new ArrayList<SelectItem>();
			for(Long key : getMapStatusBrinquedo().keySet()){
				selectItemsStatusBrinquedo.add(new SelectItem(key, getMapStatusBrinquedo().get(key), getMapStatusBrinquedo().get(key)));
			}
		}
		return selectItemsStatusBrinquedo;
	}

	public void setSelectItemsStatusBrinquedo(List<SelectItem> selectItemsStatusBrinquedo) {
		this.selectItemsStatusBrinquedo = selectItemsStatusBrinquedo;
	}

	public Map<Long, String> getMapStatusBrinquedo() {
		if(mapStatusBrinquedo == null){
			mapStatusBrinquedo = getListaStatusBrinquedoDataProvider().getMapItems();
		}
		return mapStatusBrinquedo;
	}

	public void setMapStatusBrinquedo(
			HashMap<Long, String> mapStatusBrinquedo) {
		this.mapStatusBrinquedo = mapStatusBrinquedo;
	}
	
	public ListaStatusBrinquedoDataProvider getListaStatusBrinquedoDataProvider() {
		return listaStatusBrinquedoDataProvider;
	}

	public void setListaStatusBrinquedoDataProvider(
			ListaStatusBrinquedoDataProvider listaStatusBrinquedoDataProvider) {
		this.listaStatusBrinquedoDataProvider = listaStatusBrinquedoDataProvider;
	}

	public void setMapStatusBrinquedo(Map<Long, String> mapStatusBrinquedo) {
		this.mapStatusBrinquedo = mapStatusBrinquedo;
	}
}
