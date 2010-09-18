package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.StatusDispositivo;
import qcs.base.negocio.web.dataprov.StatusDispositivoDataProvider;
import qcs.base.negocio.web.mb.StatusDispositivoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.StatusDispositivoView;

public class StatusDispositivoDataModel extends GenericDataModel<StatusDispositivoView, StatusDispositivo, Long> {
	private static final long serialVersionUID = 1L;		
	private StatusDispositivoDataProvider dataProvider;
	private StatusDispositivoMB statusDispositivoMB;

	public StatusDispositivoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(StatusDispositivoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public StatusDispositivoMB getStatusDispositivoMB() {
		return statusDispositivoMB;
	}

	public void setStatusDispositivoMB(StatusDispositivoMB statusDispositivoMB) {
		this.statusDispositivoMB = statusDispositivoMB;
	}

	@Override
	public String getDefaultSortField() {
		return "idStatusDispositivo";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getStatusDispositivoMB().getAtributosFiltros());
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
			StatusDispositivoView ret = wrappedData.get(currentPk);
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
			}if(getStatusDispositivoMB() != null && getStatusDispositivoMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<StatusDispositivoView> statusDispositivos = getDataProvider().getObjectByRange(
						getStatusDispositivoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(statusDispositivos != null && statusDispositivos.size() > 0){
					for (StatusDispositivoView statusDispositivo : statusDispositivos) {
						wrappedKeys.add(statusDispositivo.getIdStatusDispositivo());
						wrappedData.put(statusDispositivo.getIdStatusDispositivo(), statusDispositivo);
						visitor.process(context, statusDispositivo.getIdStatusDispositivo(), argument);
					}
				}
			}
		}
	}
}
