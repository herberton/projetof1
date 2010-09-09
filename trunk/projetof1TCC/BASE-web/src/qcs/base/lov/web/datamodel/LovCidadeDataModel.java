package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.base.enderecamento.web.dataprov.CidadeDataProvider;
import qcs.base.lov.web.mb.LovCidadeMB;
import qcs.datamodel.GenericDataModel;

public class LovCidadeDataModel extends GenericDataModel<CidadeView, Cidade, Long> {
	private static final long serialVersionUID = 1L;
	private CidadeDataProvider dataProvider;
	private LovCidadeMB lovCidadeMB;

	public CidadeDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(CidadeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "nomeCidade";
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
			CidadeView cidade = wrappedData.get(currentPk);
			if (cidade==null) {
				cidade = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, cidade);
				return cidade;
			} else {
				return cidade;
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

		//		if(getJSF_FASE().startsWith("RENDER_RESPONSE")){
		if (detached && getSortFieldObject() != null){   
			for (Long key : wrappedKeys){   
				setRowKey(key);   
				visitor.process(context, key, argument);   
			}
		}if(getLovCidadeMB() != null){
			int firstRow = ((SequenceRange)range).getFirstRow();
			int numberOfRows = ((SequenceRange)range).getRows();
			wrappedKeys = new ArrayList<Long>();  

			List<CidadeView> cidades = getDataProvider().getObjectByRange(
					getLovCidadeMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

			if(cidades != null && cidades.size() > 0){
				for (CidadeView cidade : cidades) {
					wrappedKeys.add(cidade.getIdCidade());
					wrappedData.put(cidade.getIdCidade(), cidade);
					visitor.process(context, cidade.getIdCidade(), argument);
				}
			}
		}
		//		}
}

	public LovCidadeMB getLovCidadeMB() {
		return lovCidadeMB;
	}

	public void setLovCidadeMB(LovCidadeMB lovCidadeMB) {
		this.lovCidadeMB = lovCidadeMB;
	}
}
