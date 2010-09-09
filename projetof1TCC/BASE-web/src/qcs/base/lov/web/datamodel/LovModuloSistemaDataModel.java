package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.lov.web.dataprov.LovModuloSistemaDataProvider;
import qcs.base.lov.web.mb.LovModuloSistemaMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.ListOfValuesModuloSistemaView;

public class LovModuloSistemaDataModel extends GenericDataModel<ListOfValuesModuloSistemaView, ListOfValuesModuloSistemaView, Long> {
	private static final long serialVersionUID = 1L;
	private LovModuloSistemaDataProvider dataProvider;
	private LovModuloSistemaMB lovModuloSistemaMB;

	public LovModuloSistemaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(LovModuloSistemaDataProvider dataProvider) {
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
			ListOfValuesModuloSistemaView moduloSistema = wrappedData.get(currentPk);
			if (moduloSistema==null) {
				moduloSistema = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, moduloSistema);
				return moduloSistema;
			} else {
				return moduloSistema;
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
		}if(getLovModuloSistemaMB() != null){
			int firstRow = ((SequenceRange)range).getFirstRow();
			int numberOfRows = ((SequenceRange)range).getRows();
			wrappedKeys = new ArrayList<Long>(); 

			List<ListOfValuesModuloSistemaView> moduloSistemas = getDataProvider().getObjectByRange(
					getLovModuloSistemaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

			if(moduloSistemas != null && moduloSistemas.size() > 0){
				for (ListOfValuesModuloSistemaView moduloSistema : moduloSistemas) {
					wrappedKeys.add(moduloSistema.getIdModulo());
					wrappedData.put(moduloSistema.getIdModulo(), moduloSistema);
					visitor.process(context, moduloSistema.getIdModulo(), argument);
				}
			}
		}
	}

	public LovModuloSistemaMB getLovModuloSistemaMB() {
		return lovModuloSistemaMB;
	}

	public void setLovModuloSistemaMB(LovModuloSistemaMB lovModuloSistemaMB) {
		this.lovModuloSistemaMB = lovModuloSistemaMB;
	}
}
