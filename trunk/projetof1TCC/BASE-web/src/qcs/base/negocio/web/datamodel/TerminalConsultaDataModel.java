package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.TerminalConsulta;
import qcs.base.negocio.web.dataprov.TerminalConsultaDataProvider;
import qcs.base.negocio.web.mb.TerminalConsultaMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.TerminalConsultaView;

public class TerminalConsultaDataModel extends GenericDataModel<TerminalConsultaView, TerminalConsulta, Long> {

	private static final long serialVersionUID = 1L;
	private TerminalConsultaDataProvider dataProvider;
	private TerminalConsultaMB terminalConsultaMB;
	//private qcs.base.negocio.web.dataprov.TerminalConsultaDataProvider dataProvider;
	
	
	//CONSTRUTOR...
	public TerminalConsultaDataModel() { super(); }
	
	
	//GET...
	public TerminalConsultaDataProvider getTerminalConsultaDataProvider() {
		return dataProvider;
	}
	public TerminalConsultaMB getTerminalConsultaMB() {
		return terminalConsultaMB;
	}
	
	
	//SET...
	public void setTerminalConsultaDataProvider(
			TerminalConsultaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	public void setTerminalConsultaMB(TerminalConsultaMB terminalConsultaMB) {
		this.terminalConsultaMB = terminalConsultaMB;
	}
	
	
	//MÉTODOS...
	@Override
	public String getDefaultSortField() {
		return "hostName";
	}

	@Override
	public int getRowCount() {
		return getTerminalConsultaDataProvider().getRowCount(getTerminalConsultaMB().getAtributosFiltros());
	}

	@Override
	public Object getRowData() {
		if (currentPk==null) {
			return null;
		} else {
			TerminalConsultaView ret = wrappedData.get(currentPk);
			if (ret==null) {
				ret = getTerminalConsultaDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, ret);
				return ret;
			} else {
				return ret;
			}
		}
	}

	@Override
	public boolean isRowAvailable() {
		if (currentPk==null) {
			return false;
		} else {
			return getTerminalConsultaDataProvider().hasObjectByPk(currentPk);
		}
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {
		if(getJSF_FASE().startsWith("RENDER_RESPONSE")){

			if (detached && getSortFieldObject() != null){   
				for (Long key : wrappedKeys){   
					setRowKey(key);   
					visitor.process(context, key, argument);   
				}
			}if(getTerminalConsultaMB() != null && getTerminalConsultaMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<TerminalConsultaView> viewList = getTerminalConsultaDataProvider().getObjectByRange(
						getTerminalConsultaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(viewList != null && viewList.size() > 0){
					for (TerminalConsultaView view : viewList) {
						wrappedKeys.add(view.getIdTerminalConsulta());
						wrappedData.put(view.getIdTerminalConsulta(), view);
						visitor.process(context, view.getIdTerminalConsulta(), argument);
					}
				}
			}
		}
	}


	public qcs.base.negocio.web.dataprov.TerminalConsultaDataProvider getDataProvider() {
		return dataProvider;
	}


	public void setDataProvider(
			qcs.base.negocio.web.dataprov.TerminalConsultaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

}
