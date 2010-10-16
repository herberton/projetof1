package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.HistoricoCliente;
import qcs.base.negocio.web.dataprov.HistoricoClienteDataProvider;
import qcs.base.negocio.web.mb.HistoricoClienteMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.HistoricoClienteView;

public class HistoricoClienteDataModel extends GenericDataModel<HistoricoClienteView, HistoricoCliente, Long> {
	private static final long serialVersionUID = 1L;		
	private HistoricoClienteDataProvider dataProvider;
	private HistoricoClienteMB historicoClienteMB;

	public HistoricoClienteDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(HistoricoClienteDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public HistoricoClienteMB getHistoricoClienteMB() {
		return historicoClienteMB;
	}

	public void setHistoricoClienteMB(HistoricoClienteMB historicoClienteMB) {
		this.historicoClienteMB = historicoClienteMB;
	}

	@Override
	public String getDefaultSortField() {
		return "idHistoricoCliente";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getHistoricoClienteMB().getAtributosFiltros());
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
			HistoricoClienteView ret = wrappedData.get(currentPk);
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
			}if(getHistoricoClienteMB() != null && getHistoricoClienteMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<HistoricoClienteView> historicoClientes = getDataProvider().getObjectByRange(
						getHistoricoClienteMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(historicoClientes != null && historicoClientes.size() > 0){
					for (HistoricoClienteView historicoCliente : historicoClientes) {
						wrappedKeys.add(historicoCliente.getId_historico_cliente());
						wrappedData.put(historicoCliente.getId_historico_cliente(), historicoCliente);
						visitor.process(context, historicoCliente.getId_historico_cliente(), argument);
					}
				}
			}
		}
	}
}
