package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.Catraca;
import qcs.base.negocio.web.dataprov.CatracaDataProvider;
import qcs.base.negocio.web.mb.CatracaMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.CatracaView;

public class CatracaDataModel extends GenericDataModel<CatracaView, Catraca, Long> {
	private static final long serialVersionUID = 1L;		
	private CatracaDataProvider dataProvider;
	private CatracaMB catracaMB;

	public CatracaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(CatracaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public CatracaMB getCatracaMB() {
		return catracaMB;
	}

	public void setCatracaMB(CatracaMB catracaMB) {
		this.catracaMB = catracaMB;
	}

	@Override
	public String getDefaultSortField() {
		return "descricao";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getCatracaMB().getAtributosFiltros());
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
			CatracaView ret = wrappedData.get(currentPk);
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
			}if(getCatracaMB() != null && getCatracaMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<CatracaView> catracas = getDataProvider().getObjectByRange(
						getCatracaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(catracas != null && catracas.size() > 0){
					for (CatracaView catraca : catracas) {
						wrappedKeys.add(catraca.getIdCatraca());
						wrappedData.put(catraca.getIdCatraca(), catraca);
						visitor.process(context, catraca.getIdCatraca(), argument);
					}
				}
			}
		}
	}
}
