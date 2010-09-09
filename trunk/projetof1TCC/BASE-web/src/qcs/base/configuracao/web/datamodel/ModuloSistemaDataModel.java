package qcs.base.configuracao.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.configuracao.web.dataprov.ModuloSistemaDataProvider;
import qcs.base.configuracao.web.mb.ModuloSistemaMB;
import qcs.base.modulo.ModuloSistema;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.ModuloSistemaView;

public class ModuloSistemaDataModel extends GenericDataModel<ModuloSistemaView, ModuloSistema, Long> {
	private static final long serialVersionUID = 1L;
	private ModuloSistemaDataProvider dataProvider;
	private ModuloSistemaMB moduloSistemaMB;

	public ModuloSistemaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ModuloSistemaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ModuloSistemaMB getModuloSistemaMB() {
		return moduloSistemaMB;
	}

	public void setModuloSistemaMB(ModuloSistemaMB moduloSistemaMB) {
		this.moduloSistemaMB = moduloSistemaMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getModuloSistemaMB().getAtributosFiltros());
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
			ModuloSistemaView ret = wrappedData.get(currentPk);
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
			}if(getModuloSistemaMB() != null && getModuloSistemaMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<ModuloSistemaView> moduloSistemas = getDataProvider().getObjectByRange(
						getModuloSistemaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(moduloSistemas != null && moduloSistemas.size() > 0){
					for (ModuloSistemaView moduloSistema : moduloSistemas) {
						wrappedKeys.add(moduloSistema.getIdModulo());
						wrappedData.put(moduloSistema.getIdModulo(), moduloSistema);
						visitor.process(context, moduloSistema.getIdModulo(), argument);
					}
				}
			}
		}

	}
}
