package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.StatusCliente;
import qcs.base.negocio.web.dataprov.StatusClienteDataProvider;
import qcs.base.negocio.web.mb.StatusClienteMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.StatusClienteView;

public class StatusClienteDataModel extends GenericDataModel<StatusClienteView, StatusCliente, Long> {
	private static final long serialVersionUID = 1L;		
	private StatusClienteDataProvider dataProvider;
	private StatusClienteMB statusClienteMB;

	public StatusClienteDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(StatusClienteDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public StatusClienteMB getStatusClienteMB() {
		return statusClienteMB;
	}

	public void setStatusClienteMB(StatusClienteMB statusClienteMB) {
		this.statusClienteMB = statusClienteMB;
	}

	@Override
	public String getDefaultSortField() {
		return "idStatusCliente";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getStatusClienteMB().getAtributosFiltros());
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
			StatusClienteView ret = wrappedData.get(currentPk);
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
			}if(getStatusClienteMB() != null && getStatusClienteMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<StatusClienteView> statusClientes = getDataProvider().getObjectByRange(
						getStatusClienteMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(statusClientes != null && statusClientes.size() > 0){
					for (StatusClienteView statusCliente : statusClientes) {
						wrappedKeys.add(statusCliente.getIdStatusCliente());
						wrappedData.put(statusCliente.getIdStatusCliente(), statusCliente);
						visitor.process(context, statusCliente.getIdStatusCliente(), argument);
					}
				}
			}
		}
	}
}
