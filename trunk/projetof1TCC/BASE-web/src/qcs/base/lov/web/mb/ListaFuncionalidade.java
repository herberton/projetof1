package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.dataprov.ListaFuncionalidadeDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;

public class ListaFuncionalidade implements Serializable{
	protected static Log log = LogFactory.getLog(ListaFuncionalidade.class);
	private static final long serialVersionUID = 1L;
	private ListaFuncionalidadeDataProvider listaFuncionalidadeDataProvider;

	private Map<Long, String> mapFuncionalidade;
	private List<SelectItem> selectItemsFuncionalidade;

	public List<SelectItem> getSelectItemsFuncionalidade() {
		try{
			if(selectItemsFuncionalidade == null){
				selectItemsFuncionalidade = new ArrayList<SelectItem>();
				for(Long key : getMapFuncionalidade().keySet()){
					selectItemsFuncionalidade.add(new SelectItem(key, getMapFuncionalidade().get(key)));
				}
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de funcionalidades", e);
			GeneralMessagesUtil.incluirMensagem("funcionalidade","Erro ao caregar Lista de funcionalidades", e);
		}
		return selectItemsFuncionalidade;
	}

	public void setSelectItemsFuncionalidade(List<SelectItem> selectItemsFuncionalidadees) {
		this.selectItemsFuncionalidade = selectItemsFuncionalidadees;
	}

	public Map<Long, String> getMapFuncionalidade() {
		try{
			if(mapFuncionalidade == null){
				mapFuncionalidade = getListaFuncionalidadeDataProvider().getMapItems();
			}
		}catch(Exception e){
			log.error("Erro ao caregar Lista de funcionalidades", e);
			GeneralMessagesUtil.incluirMensagem("funcionalidade","Erro ao caregar Lista de funcionalidades", e);
		}
		return mapFuncionalidade;
	}

	public void setMapFuncionalidade(
			Map<Long, String> mapFuncionalidade) {
		this.mapFuncionalidade = mapFuncionalidade;
	}

	public ListaFuncionalidadeDataProvider getListaFuncionalidadeDataProvider() {
		return listaFuncionalidadeDataProvider;
	}

	public void setListaFuncionalidadeDataProvider(
			ListaFuncionalidadeDataProvider listaFuncionalidadeDataProvider) {
		this.listaFuncionalidadeDataProvider = listaFuncionalidadeDataProvider;
	}
}
