package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import qcs.base.lov.web.dataprov.ListaModuloSistemaDataProvider;

public class ListaModuloSistema implements Serializable{
	private static final long serialVersionUID = 1L;
	private ListaModuloSistemaDataProvider listaModuloSistemaDataProvider;

	private Map<Long, String> mapModuloSistema;
	private List<SelectItem> selectItemsModuloSistema;
	
	public List<SelectItem> getSelectItemsModuloSistema() {
		if(selectItemsModuloSistema == null){
			selectItemsModuloSistema = new ArrayList<SelectItem>();
			for(Long key : getMapModuloSistema().keySet()){
				selectItemsModuloSistema.add(new SelectItem(key, getMapModuloSistema().get(key)));
			}
		}
		return selectItemsModuloSistema;
	}

	public void setSelectItemsModuloSistema(List<SelectItem> selectItemsModuloSistemaes) {
		this.selectItemsModuloSistema = selectItemsModuloSistemaes;
	}

	public Map<Long, String> getMapModuloSistema() {
		if(mapModuloSistema == null){
			mapModuloSistema = getListaModuloSistemaDataProvider().getMapItems();
		}
		return mapModuloSistema;
	}

	public void setMapModuloSistema(
			Map<Long, String> mapModuloSistema) {
		this.mapModuloSistema = mapModuloSistema;
	}
	
	public ListaModuloSistemaDataProvider getListaModuloSistemaDataProvider() {
		return listaModuloSistemaDataProvider;
	}

	public void setListaModuloSistemaDataProvider(
			ListaModuloSistemaDataProvider listaModuloSistemaDataProvider) {
		this.listaModuloSistemaDataProvider = listaModuloSistemaDataProvider;
	}
}
