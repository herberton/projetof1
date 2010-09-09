package qcs.base.seguranca.view.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;


public enum StatusUsuarioEnum {
	A("Ativo"),
	B("Bloqueado"),
	I("Inativo"),
	T("Temporário");

	private final String desc;
	private List<SelectItem> selectItemsStatusUsuario;
	private HashMap<String, String> mapStatusUsuario;
	
	StatusUsuarioEnum(String desc) {   
		this.desc = desc;   
	}

	public String getDesc() {
		return desc;
	}
	
	public List<SelectItem> getSelectItemsStatusUsuario() {
		if(selectItemsStatusUsuario == null){
			selectItemsStatusUsuario = new ArrayList<SelectItem>();
			for(String key : getMapStatusUsuario().keySet()){
				selectItemsStatusUsuario.add(
						new SelectItem(key, getMapStatusUsuario().get(key)));
			}
		}
		return selectItemsStatusUsuario;
	}

	//SITUACÕES
	public HashMap<String, String> getMapStatusUsuario() {
		if(mapStatusUsuario == null){
			mapStatusUsuario = new HashMap<String, String>();
			mapStatusUsuario.put("A", "Ativo");
			mapStatusUsuario.put("B", "Bloqueado");
			mapStatusUsuario.put("I", "Inativo");
			mapStatusUsuario.put("T", "Temporário");
		}
		return mapStatusUsuario;
	}
}
