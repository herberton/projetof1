package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.Brinquedo;
import qcs.base.negocio.web.dataprov.BrinquedoDataProvider;
import qcs.base.negocio.web.mb.BrinquedoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.BrinquedoView;

public class BrinquedoDataModel extends GenericDataModel<BrinquedoView, Brinquedo, Long> {
	private static final long serialVersionUID = 1L;		
	private BrinquedoDataProvider dataProvider;
	private BrinquedoMB brinquedoMB;

	public BrinquedoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(BrinquedoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public BrinquedoMB getBrinquedoMB() {
		return brinquedoMB;
	}

	public void setBrinquedoMB(BrinquedoMB brinquedoMB) {
		this.brinquedoMB = brinquedoMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getBrinquedoMB().getAtributosFiltros());
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
			BrinquedoView ret = wrappedData.get(currentPk);
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
			}if(getBrinquedoMB() != null && getBrinquedoMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<BrinquedoView> brinquedos = getDataProvider().getObjectByRange(
						getBrinquedoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(brinquedos != null && brinquedos.size() > 0){
					for (BrinquedoView brinquedo : brinquedos) {
						wrappedKeys.add(brinquedo.getIdBrinquedo());
						wrappedData.put(brinquedo.getIdBrinquedo(), brinquedo);
						visitor.process(context, brinquedo.getIdBrinquedo(), argument);
					}
				}
			}
		}
	}
}
