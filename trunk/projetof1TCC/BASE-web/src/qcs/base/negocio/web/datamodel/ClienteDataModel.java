package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.Cliente;
import qcs.base.negocio.web.dataprov.ClienteDataProvider;
import qcs.base.negocio.web.mb.ClienteMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.ClienteView;

public class ClienteDataModel extends GenericDataModel<ClienteView, Cliente, Long> {
	private static final long serialVersionUID = 1L;		
	private ClienteDataProvider dataProvider;
	private ClienteMB clienteMB;
	private qcs.base.negocio.web.dataprov.ClienteDataProvider clienteDataProvider;

	public ClienteDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ClienteDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ClienteMB getClienteMB() {
		return clienteMB;
	}

	public void setClienteMB(ClienteMB clienteMB) {
		this.clienteMB = clienteMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getClienteMB().getAtributosFiltros());
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
			ClienteView ret = wrappedData.get(currentPk);
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
			}if(getClienteMB() != null && getClienteMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<ClienteView> clientes = getDataProvider().getObjectByRange(
						getClienteMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(clientes != null && clientes.size() > 0){
					for (ClienteView cliente : clientes) {
						wrappedKeys.add(cliente.getIdCliente());
						wrappedData.put(cliente.getIdCliente(), cliente);
						visitor.process(context, cliente.getIdCliente(), argument);
					}
				}
			}
		}
	}

	public qcs.base.negocio.web.dataprov.ClienteDataProvider getClienteDataProvider() {
		return clienteDataProvider;
	}

	public void setClienteDataProvider(
			qcs.base.negocio.web.dataprov.ClienteDataProvider clienteDataProvider) {
		this.clienteDataProvider = clienteDataProvider;
	}
}
