package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.lov.web.dataprov.LovDispositivoDataProvider;
import qcs.base.lov.web.mb.LovDispositivoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.LovDispositivoView;

public class LovDispositivoDataModel extends GenericDataModel<LovDispositivoView, LovDispositivoView, Long> {
	private static final long serialVersionUID = 1L;
	private LovDispositivoDataProvider dataProvider; 
	private LovDispositivoMB lovDispositivoMB;

	public LovDispositivoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(LovDispositivoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "idDispositivo";
	}

	@Override
	public int getRowCount() {
		try{
			return getDataProvider().getRowCount(getLovDispositivoMB().getAtributosFiltros());
		}catch(Exception e){
			log.debug("Erro LovDispositivoDataModel : getRowCount", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovDispositivoDataModel : getRowCount", e.getMessage()));
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
			LovDispositivoView lovDispositivoView = wrappedData.get(currentPk);
			if (lovDispositivoView==null) {
				lovDispositivoView = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, lovDispositivoView);
				return lovDispositivoView;
			} else {
				return lovDispositivoView;
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
				}if(getLovDispositivoMB() != null){
					int firstRow = ((SequenceRange)range).getFirstRow();
					int numberOfRows = ((SequenceRange)range).getRows();
					wrappedKeys = new ArrayList<Long>(); 

					List<LovDispositivoView> lovDispositivos = getDataProvider().getObjectByRange(
							getLovDispositivoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

					if(lovDispositivos != null && lovDispositivos.size() > 0){
						for (LovDispositivoView lovDispositivoView : lovDispositivos) {
							wrappedKeys.add(lovDispositivoView.getIdDispositivo());
							wrappedData.put(lovDispositivoView.getIdDispositivo(), lovDispositivoView);
							visitor.process(context, lovDispositivoView.getIdDispositivo(), argument);
						}
					}
				}
//			}
		}catch(Exception e){
			log.debug("Erro LovDispositivoDataModel : walk", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovDispositivoDataModel : walk", e.getMessage()));
		}
	}

	public LovDispositivoMB getLovDispositivoMB() {
		return lovDispositivoMB;
	}

	public void setLovDispositivoMB(LovDispositivoMB lovDispositivoMB) {
		this.lovDispositivoMB = lovDispositivoMB;
	}

}
