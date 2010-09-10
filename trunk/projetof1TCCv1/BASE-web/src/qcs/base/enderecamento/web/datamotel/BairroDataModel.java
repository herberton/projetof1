package qcs.base.enderecamento.web.datamotel;

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
import qcs.base.enderecamento.web.mb.BairroMB;
import qcs.datamodel.GenericDataModel;

public class BairroDataModel extends GenericDataModel<BairroView, Bairro, Long> {
	private static final long serialVersionUID = 1L;
	private BairroDataProvider dataProvider;
	private BairroMB bairroMB;

	public BairroDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(BairroDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public BairroMB getBairroMB() {
		return bairroMB;
	}

	public void setBairroMB(BairroMB bairroMB) {
		this.bairroMB = bairroMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getBairroMB().getAtributosFiltros());
	}

	/**
	 * This is main part of Visitor pattern. Method called by framework many times during request processing. 
	 */
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {
		if(getJSF_FASE().startsWith("RENDER_RESPONSE")){

			if (detached && getSortFieldObject() != null){   
				for (Long key : wrappedKeys){   
					setRowKey(key);   
					visitor.process(context, key, argument);   
				}
			}if(getBairroMB() != null && getBairroMB().isPesquisarState()){
				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();

				rowCount = new Integer(getDataProvider().getRowCount(getBairroMB().getAtributosFiltros()));

				List<BairroView> bairros = getDataProvider().getObjectByRange(
						getBairroMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(bairros != null && bairros.size() > 0){
					for (BairroView bairro : bairros) {
						wrappedKeys.add(bairro.getIdBairro());
						wrappedData.put(bairro.getIdBairro(), bairro);
						visitor.process(context, bairro.getIdBairro(), argument);
					}
				}
			}
		}
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
			BairroView ret = wrappedData.get(currentPk);
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
}
