package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.lov.web.dataprov.LovBrinquedoDataProvider;
import qcs.base.lov.web.mb.LovBrinquedoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.LovBrinquedoView;

public class LovBrinquedoDataModel extends GenericDataModel<LovBrinquedoView, LovBrinquedoView, Long> {
	private static final long serialVersionUID = 1L;
	private LovBrinquedoDataProvider dataProvider; 
	private LovBrinquedoMB lovBrinquedoMB;

	public LovBrinquedoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(LovBrinquedoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		try{
			return getDataProvider().getRowCount(getLovBrinquedoMB().getAtributosFiltros());
		}catch(Exception e){
			log.debug("Erro LovBrinquedoDataModel : getRowCount", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovBrinquedoDataModel : getRowCount", e.getMessage()));
		}
		return 0;
	}

	/**
	 * This is main way to obtain data row. It is intensively used by framework. 
	 * We strongly recommend use of local cache in that method. 
	 */
	@Override
	public Object getRowData() {	   
		if (currentPk==null) {
			return null;
		} else {
			LovBrinquedoView lovBrinquedoView = wrappedData.get(currentPk);
			if (lovBrinquedoView==null) {
				lovBrinquedoView = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, lovBrinquedoView);
				return lovBrinquedoView;
			} else {
				return lovBrinquedoView;
			}
		}
	}

	/**
	 * Never called by framework.
	 */
	@Override
	public boolean isRowAvailable() {	
		if (currentPk==null) {
			return false;
		} else {
			return getDataProvider().hasObjectByPk(currentPk);
		}
	}

	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) throws IOException {
		try{
//			if(getJSF_FASE().startsWith("RENDER_RESPONSE")){		
				if (detached && getSortFieldObject() != null){   
					for (Long key : wrappedKeys){   
						setRowKey(key);   
						visitor.process(context, key, argument);   
					}
				}if(getLovBrinquedoMB() != null){
					int firstRow = ((SequenceRange)range).getFirstRow();
					int numberOfRows = ((SequenceRange)range).getRows();
					wrappedKeys = new ArrayList<Long>(); 

					List<LovBrinquedoView> lovBrinquedos = getDataProvider().getObjectByRange(
							getLovBrinquedoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

					if(lovBrinquedos != null && lovBrinquedos.size() > 0){
						for (LovBrinquedoView lovBrinquedoView : lovBrinquedos) {
							wrappedKeys.add(lovBrinquedoView.getIdBrinquedo());
							wrappedData.put(lovBrinquedoView.getIdBrinquedo(), lovBrinquedoView);
							visitor.process(context, lovBrinquedoView.getIdBrinquedo(), argument);
						}
					}
				}
//			}
		}catch(Exception e){
			log.debug("Erro LovBrinquedoDataModel : walk", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovBrinquedoDataModel : walk", e.getMessage()));
		}
	}

	public LovBrinquedoMB getLovBrinquedoMB() {
		return lovBrinquedoMB;
	}

	public void setLovBrinquedoMB(LovBrinquedoMB lovBrinquedoMB) {
		this.lovBrinquedoMB = lovBrinquedoMB;
	}

}
