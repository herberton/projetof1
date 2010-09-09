package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.dataprov.ListaValorTabAuxDataProvider;
import qcs.base.tabaux.ValorTabAux;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class ListaValorTabAux implements Serializable{
	protected static Log log = LogFactory.getLog(ListaValorTabAux.class);
	private static final long serialVersionUID = 1L;
	private ListaValorTabAuxDataProvider listaValorTabAuxDataProvider;

	//map
	private Map<Long, String> mapItemsUf;
	private Map<Long, String> mapItemsTipoLog;
	private HashMap<Long, String> mapTipoPessoa;

	//selectItems
	private List<SelectItem> selectItemsUf;
	private List<SelectItem> selectItemsTipoLog;
	private List<SelectItem> selectItemsTipoPessoas;


	public List<SelectItem> getSelectItemsUf() {
		try{
			if(selectItemsUf == null){
				selectItemsUf = new ArrayList<SelectItem>();
				for(Long key : getMapItemsUf().keySet()){
					selectItemsUf.add(new SelectItem(key, getMapItemsUf().get(key)));
				}
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de UFs", e);
			GeneralMessagesUtil.incluirMensagem(null,"Erro ao caregar Lista de UFs", e);
		}
		return selectItemsUf;
	}

	public void setSelectItemsUf(List<SelectItem> selectItemsUf) {
		this.selectItemsUf = selectItemsUf;
	}

	public List<SelectItem> getSelectItemsTipoLog() {
		try{
			if(selectItemsTipoLog == null){
				selectItemsTipoLog = new ArrayList<SelectItem>();
				for(Long key : getMapItemsTipoLog().keySet()){
					selectItemsTipoLog.add(new SelectItem(key, getMapItemsTipoLog().get(key)));
				}
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de tipo logradouro", e);
			GeneralMessagesUtil.incluirMensagem(null,"Erro ao caregar Lista de tipo logradouro", e);
		}
		return selectItemsTipoLog;
	}

	public void setSelectItemsTipoLog(List<SelectItem> selectItemsTipoLog) {
		this.selectItemsTipoLog = selectItemsTipoLog;
	}

	public Map<Long, String> getMapItemsUf() throws InfrastructureException, Exception{
		if(mapItemsUf == null){
			mapItemsUf = getListaValorTabAuxDataProvider().getMapItemsUf();
		}
		return mapItemsUf;
	}

	public void setMapItemsUf(
			HashMap<Long, String> mapItemsUf) {
		this.mapItemsUf = mapItemsUf;
	}

	public Map<Long, String> getMapItemsTipoLog() throws InfrastructureException, Exception {
		if(mapItemsTipoLog == null){
			mapItemsTipoLog = getListaValorTabAuxDataProvider().getMapItemsTipoLog();
		}
		return mapItemsTipoLog;
	}

	public void setMapItemsTipoLog(
			HashMap<Long, String> mapItemsTipoLog) {
		this.mapItemsTipoLog = mapItemsTipoLog;
	}

	public ListaValorTabAuxDataProvider getListaValorTabAuxDataProvider() {
		return listaValorTabAuxDataProvider;
	}

	public void setListaValorTabAuxDataProvider(
			ListaValorTabAuxDataProvider listaValorTabAuxDataProvider) {
		this.listaValorTabAuxDataProvider = listaValorTabAuxDataProvider;
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

	public void setSelectItemsTipoPessoaes(List<SelectItem> selectItemsTipoPessoas) {
		this.selectItemsTipoPessoas = selectItemsTipoPessoas;
	}

	public HashMap<Long, String> getMapTipoPessoa() throws InfrastructureException, Exception {
		if(mapTipoPessoa == null){
			mapTipoPessoa = new HashMap<Long, String>();
			for(ValorTabAux valTabAux : getListaValorTabAuxDataProvider().getAllItems()){
				mapTipoPessoa.put(valTabAux.getIdValTabAux(), valTabAux.getNome());
			}
		}
		return mapTipoPessoa;
	}

	public void setMapTipoPessoa(
			HashMap<Long, String> mapTipoPessoa) {
		this.mapTipoPessoa = mapTipoPessoa;
	}
}
