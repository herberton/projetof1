package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.StatusBrinquedo;
import qcs.base.negocio.web.dataprov.StatusBrinquedoDataProvider;
import qcs.base.negocio.web.mb.StatusBrinquedoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;

public class StatusBrinquedoDataModel extends GenericDataModel<StatusBrinquedoView, StatusBrinquedo, Long> {
	private static final long serialVersionUID = 1L;		
	private StatusBrinquedoDataProvider dataProvider;
	private StatusBrinquedoMB statusBrinquedoMB;

	public StatusBrinquedoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(StatusBrinquedoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public StatusBrinquedoMB getStatusBrinquedoMB() {
		return statusBrinquedoMB;
	}

	public void setStatusBrinquedoMB(StatusBrinquedoMB statusBrinquedoMB) {
		this.statusBrinquedoMB = statusBrinquedoMB;
	}

	@Override
	public String getDefaultSortField() {
		return "idStatusBrinquedo";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getStatusBrinquedoMB().getAtributosFiltros());
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
			StatusBrinquedoView ret = wrappedData.get(currentPk);
			if (ret==null) {
				ret = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, ret);
				return ret;
			} else {
				return ret;
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
		if(getJSF_FASE().startsWith("RENDER_RESPONSE")){

			if (detached && getSortFieldObject() != null){   
				for (Long key : wrappedKeys){   
					setRowKey(key);   
					visitor.process(context, key, argument);   
				}
			}if(getStatusBrinquedoMB() != null && getStatusBrinquedoMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<StatusBrinquedoView> statusBrinquedos = getDataProvider().getObjectByRange(
						getStatusBrinquedoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(statusBrinquedos != null && statusBrinquedos.size() > 0){
					for (StatusBrinquedoView statusBrinquedo : statusBrinquedos) {
						wrappedKeys.add(statusBrinquedo.getIdStatusBrinquedo());
						wrappedData.put(statusBrinquedo.getIdStatusBrinquedo(), statusBrinquedo);
						visitor.process(context, statusBrinquedo.getIdStatusBrinquedo(), argument);
					}
				}
			}
		}
	}
}
