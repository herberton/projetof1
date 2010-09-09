package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.persistence.view.BairroView;
import qcs.base.enderecamento.web.dataprov.BairroDataProvider;
import qcs.base.lov.web.mb.LovBairroMB;
import qcs.datamodel.GenericDataModel;

public class LovBairroDataModel extends GenericDataModel<BairroView, Bairro, Long> {
	private static final long serialVersionUID = 1L;
	private BairroDataProvider dataProvider;
	private LovBairroMB lovBairroMB;

	public BairroDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(BairroDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(null, null);
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
			BairroView bairro = wrappedData.get(currentPk);
			if (bairro==null) {
				bairro = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, bairro);
				return bairro;
			} else {
				return bairro;
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
		
		if (detached && getSortFieldObject() != null){   
			for (Long key : wrappedKeys){   
				setRowKey(key);   
				visitor.process(context, key, argument);   
			}
		}if(getLovBairroMB() != null){
			int firstRow = ((SequenceRange)range).getFirstRow();
			int numberOfRows = ((SequenceRange)range).getRows();
			wrappedKeys = new ArrayList<Long>();  

			List<BairroView> bairros = getDataProvider().getObjectByRange(
					getLovBairroMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

			if(bairros != null && bairros.size() > 0){
				for (BairroView bairro : bairros) {
					wrappedKeys.add(bairro.getIdBairro());
					wrappedData.put(bairro.getIdBairro(), bairro);
					visitor.process(context, bairro.getIdBairro(), argument);
				}
			}
		}
}

	public LovBairroMB getLovBairroMB() {
		return lovBairroMB;
	}

	public void setLovBairroMB(LovBairroMB lovBairroMB) {
		this.lovBairroMB = lovBairroMB;
	}
}
