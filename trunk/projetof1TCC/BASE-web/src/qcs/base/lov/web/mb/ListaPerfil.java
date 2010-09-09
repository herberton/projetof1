package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.dataprov.ListaPerfilDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;

public class ListaPerfil implements Serializable{
	protected static Log log = LogFactory.getLog(ListaPerfil.class);
	private static final long serialVersionUID = 1L;
	private ListaPerfilDataProvider listaPerfilDataProvider;

	private Map<Long, String> mapPerfil;
	private List<SelectItem> selectItemsPerfil;

	public List<SelectItem> getSelectItemsPerfil() {
		try{
			if(selectItemsPerfil == null){
				selectItemsPerfil = new ArrayList<SelectItem>();
				for(Long key : getMapPerfil().keySet()){
					selectItemsPerfil.add(new SelectItem(key, getMapPerfil().get(key)));
				}
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de perfis", e);
			GeneralMessagesUtil.incluirMensagem("perfil","Erro ao caregar Lista de perfis", e);
		}
		return selectItemsPerfil;
	}

	public void setSelectItemsPerfil(List<SelectItem> selectItemsPerfiles) {
		this.selectItemsPerfil = selectItemsPerfiles;
	}

	public Map<Long, String> getMapPerfil() {
		try{
			if(mapPerfil == null){
				mapPerfil = getListaPerfilDataProvider().getMapItems();
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de perfis", e);
			GeneralMessagesUtil.incluirMensagem("perfil","Erro ao caregar Lista de perfis", e);
		}
		return mapPerfil;
	}

	public void setMapPerfil(
			Map<Long, String> mapPerfil) {
		this.mapPerfil = mapPerfil;
	}

	public ListaPerfilDataProvider getListaPerfilDataProvider() {
		return listaPerfilDataProvider;
	}

	public void setListaPerfilDataProvider(
			ListaPerfilDataProvider listaPerfilDataProvider) {
		this.listaPerfilDataProvider = listaPerfilDataProvider;
	}
}
