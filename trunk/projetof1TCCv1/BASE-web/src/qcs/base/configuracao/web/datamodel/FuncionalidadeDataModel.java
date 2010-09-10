package qcs.base.configuracao.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.configuracao.web.dataprov.FuncionalidadeDataProvider;
import qcs.base.configuracao.web.mb.FuncionalidadeMB;
import qcs.base.modulo.Funcionalidade;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.FuncionalidadeView;

public class FuncionalidadeDataModel extends GenericDataModel<FuncionalidadeView, Funcionalidade, Long> {
	private static final long serialVersionUID = 1L;		
	private FuncionalidadeDataProvider dataProvider;
	private FuncionalidadeMB funcionalidadeMB;

	public FuncionalidadeDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(FuncionalidadeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public FuncionalidadeMB getFuncionalidadeMB() {
		return funcionalidadeMB;
	}

	public void setFuncionalidadeMB(FuncionalidadeMB funcionalidadeMB) {
		this.funcionalidadeMB = funcionalidadeMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getFuncionalidadeMB().getAtributosFiltros());
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
			FuncionalidadeView ret = wrappedData.get(currentPk);
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
			}if(getFuncionalidadeMB() != null && getFuncionalidadeMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<FuncionalidadeView> funcionalidades = getDataProvider().getObjectByRange(
						getFuncionalidadeMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(funcionalidades != null && funcionalidades.size() > 0){
					for (FuncionalidadeView funcionalidade : funcionalidades) {
						wrappedKeys.add(funcionalidade.getIdFuncionalidade());
						wrappedData.put(funcionalidade.getIdFuncionalidade(), funcionalidade);
						visitor.process(context, funcionalidade.getIdFuncionalidade(), argument);
					}
				}
			}
		}
	}
}
