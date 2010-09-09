package qcs.base.web.aplicacao.mb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AplicacaoSkin {
	protected static Log log = LogFactory.getLog(AplicacaoSkin.class);
	private String skinAtual;
	private List<SelectItem> selectItemsQuasarSkins;
	private HashMap<String, String> mapQuasarSkins;
	
	public String getSkinAtual() {
		return skinAtual;
	}

	public void setSkinAtual(String skinAtual) {
		this.skinAtual = skinAtual;
	}

	public List<SelectItem> getSelectItemsQuasarSkins() {
		if(selectItemsQuasarSkins == null){
			selectItemsQuasarSkins = new ArrayList<SelectItem>();
			for(String keySkin : getMapQuasarSkins().keySet()){
				selectItemsQuasarSkins.add(new SelectItem(keySkin, getMapQuasarSkins().get(keySkin)));
			}
		}
		return selectItemsQuasarSkins;
	}

	public void setSelectItemsQuasarSkins(List<SelectItem> selectItemsQuasarSkins) {
		this.selectItemsQuasarSkins = selectItemsQuasarSkins;
	}

	public HashMap<String, String> getMapQuasarSkins() {
		if(mapQuasarSkins == null){
			mapQuasarSkins = new HashMap<String, String>();
			String paramQuasarSkins = (String)FacesContext.getCurrentInstance().getExternalContext().getInitParameter("quasar.skins");
			StringTokenizer tokenizer = new StringTokenizer(paramQuasarSkins, ";");
			
			while(tokenizer.hasMoreTokens()){
				StringTokenizer tokenizerInterno = new StringTokenizer(tokenizer.nextToken(), ",");
				String key = tokenizerInterno.nextToken();
				String value = tokenizerInterno.nextToken();
				log.debug("Inserindo Skin[key="+key+",value="+value+"]");
				mapQuasarSkins.put(key, value);
			}
		}
		
		return mapQuasarSkins;
	}

	public void setMapQuasarSkins(HashMap<String, String> mapQuasarSkins) {
		this.mapQuasarSkins = mapQuasarSkins;
	}

}
