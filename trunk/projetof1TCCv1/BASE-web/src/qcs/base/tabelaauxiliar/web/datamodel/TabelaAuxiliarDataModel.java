package qcs.base.tabelaauxiliar.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.base.tabelaauxiliar.web.dataprov.TabelaAuxiliarDataProvider;
import qcs.base.tabelaauxiliar.web.mb.TabelaAuxiliarMB;
import qcs.base.web.datamodel.GenericDataModel;


public class TabelaAuxiliarDataModel extends GenericDataModel<TabelaAuxiliarView, TabelaAuxiliar, Long> {
	private static final long serialVersionUID = 1L;

	private TabelaAuxiliarDataProvider dataProvider;
	private TabelaAuxiliarMB tabelaAuxiliarMB;


	public TabelaAuxiliarDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(TabelaAuxiliarDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "idTabAux";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getTabelaAuxiliarMB().getAtributosFiltros());
	}
	
	public TabelaAuxiliarMB getTabelaAuxiliarMB() {
		return tabelaAuxiliarMB;
	}

	public void setTabelaAuxiliarMB(TabelaAuxiliarMB tabelaAuxiliarMB) {
		this.tabelaAuxiliarMB = tabelaAuxiliarMB;
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
			TabelaAuxiliarView ret = wrappedData.get(currentPk);
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
			}if(getTabelaAuxiliarMB() != null && getTabelaAuxiliarMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();


				rowCount = new Integer(getDataProvider().getRowCount(getTabelaAuxiliarMB().getAtributosFiltros()));

				List<TabelaAuxiliarView> tabelaAuxiliars = getDataProvider().getObjectByRange(
						getTabelaAuxiliarMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(tabelaAuxiliars != null && tabelaAuxiliars.size() > 0){
					for (TabelaAuxiliarView tabelaAuxiliar : tabelaAuxiliars) {						
						wrappedKeys.add(tabelaAuxiliar.getIdTabAux());						
						wrappedData.put(tabelaAuxiliar.getIdTabAux(), tabelaAuxiliar);
						visitor.process(context, tabelaAuxiliar.getIdTabAux(), argument);
					}
				}
			}
		}
	}
}
