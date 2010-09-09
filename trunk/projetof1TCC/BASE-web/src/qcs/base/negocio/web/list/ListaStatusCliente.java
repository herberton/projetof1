package qcs.base.negocio.web.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

public class ListaStatusCliente implements Serializable{
	private static final long serialVersionUID = 1L;
	private ListaStatusClienteDataProvider listaStatusClienteDataProvider;

	private Map<Long, String> mapStatusCliente;
	private List<SelectItem> selectItemsStatusCliente;
	
	public List<SelectItem> getSelectItemsStatusCliente() {
		if(selectItemsStatusCliente == null){
			selectItemsStatusCliente = new ArrayList<SelectItem>();
			for(Long key : getMapStatusCliente().keySet()){
				selectItemsStatusCliente.add(new SelectItem(key, getMapStatusCliente().get(key), getMapStatusCliente().get(key)));
			}
		}
		return selectItemsStatusCliente;
	}

	public void setSelectItemsStatusCliente(List<SelectItem> selectItemsStatusCliente) {
		this.selectItemsStatusCliente = selectItemsStatusCliente;
	}

	public Map<Long, String> getMapStatusCliente() {
		if(mapStatusCliente == null){
			mapStatusCliente = getListaStatusClienteDataProvider().getMapItems();
		}
		return mapStatusCliente;
	}

	public void setMapStatusCliente(
			HashMap<Long, String> mapStatusCliente) {
		this.mapStatusCliente = mapStatusCliente;
	}
	
	public ListaStatusClienteDataProvider getListaStatusClienteDataProvider() {
		return listaStatusClienteDataProvider;
	}

	public void setListaStatusClienteDataProvider(
			ListaStatusClienteDataProvider listaStatusClienteDataProvider) {
		this.listaStatusClienteDataProvider = listaStatusClienteDataProvider;
	}

	public void setMapStatusCliente(Map<Long, String> mapStatusCliente) {
		this.mapStatusCliente = mapStatusCliente;
	}
}
