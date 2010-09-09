package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.dataprov.ListaValorTabAuxDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;

public class ListaTipoPessoa implements Serializable{
	protected static Log log = LogFactory.getLog(ListaTipoPessoa.class);
	private static final long serialVersionUID = 1L;
	private ListaValorTabAuxDataProvider dataProvider;

	private Map<Long, String> mapTipoPessoa;
	private List<SelectItem> selectItemsTipoPessoas;

	public void setDataProvider(
			ListaValorTabAuxDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public List<SelectItem> getSelectItemsTipoPessoas() {
		try{
			if(selectItemsTipoPessoas == null){
				selectItemsTipoPessoas = new ArrayList<SelectItem>();
				for(Long key : getMapTipoPessoa().keySet()){
					selectItemsTipoPessoas.add(new SelectItem(key, getMapTipoPessoa().get(key)));
				}
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de pessoas", e);
			GeneralMessagesUtil.incluirMensagem("pessoa","Erro ao caregar Lista de pessoas", e);
		}
		return selectItemsTipoPessoas;
	}

	public void setSelectItemsTipoPessoas(List<SelectItem> selectItemsTipoPessoas) {
		this.selectItemsTipoPessoas = selectItemsTipoPessoas;
	}

	public Map<Long, String> getMapTipoPessoa() {
		try{
			if(mapTipoPessoa == null){
				mapTipoPessoa = getDataProvider().getMapItemsTipoPessoa();
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de pessoas", e);
			GeneralMessagesUtil.incluirMensagem("pessoa","Erro ao caregar Lista de pessoas", e);
		}
		return mapTipoPessoa;
	}

	public void setMapTipoPessoa(
			HashMap<Long, String> mapTipoPessoa) {
		this.mapTipoPessoa = mapTipoPessoa;
	}

	public ListaValorTabAuxDataProvider getDataProvider() {
		if(dataProvider == null){
			FacesContext context = FacesContext.getCurrentInstance();
			ELResolver resolver = context.getApplication().getELResolver();  
			dataProvider = (ListaValorTabAuxDataProvider) resolver.getValue(context.getELContext(), null, "listaValorTabAuxDataProvider");
		}
		return dataProvider;
	}
}
