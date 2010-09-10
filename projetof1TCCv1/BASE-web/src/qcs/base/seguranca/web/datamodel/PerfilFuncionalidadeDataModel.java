package qcs.base.seguranca.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.seguranca.PerfilFuncionalidade;
import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;
import qcs.base.seguranca.web.dataprov.PerfilFuncionalidadeDataProvider;
import qcs.base.seguranca.web.mb.PerfilFuncionalidadeMB;
import qcs.base.web.datamodel.GenericDataModel;

public class PerfilFuncionalidadeDataModel extends GenericDataModel<PerfilFuncionalidadeView, PerfilFuncionalidade, Long> {
	private static final long serialVersionUID = 1L;		
	private PerfilFuncionalidadeDataProvider dataProvider;
	private PerfilFuncionalidadeMB perfilFuncionalidadeMB;

	public PerfilFuncionalidadeDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PerfilFuncionalidadeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public PerfilFuncionalidadeMB getPerfilFuncionalidadeMB() {
		return perfilFuncionalidadeMB;
	}

	public void setPerfilFuncionalidadeMB(PerfilFuncionalidadeMB perfilFuncionalidadeMB) {
		this.perfilFuncionalidadeMB = perfilFuncionalidadeMB;
	}

	@Override
	public String getDefaultSortField() {
		return "funcionalidade.nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getPerfilFuncionalidadeMB().getAtributosFiltros());
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
			PerfilFuncionalidadeView ret = wrappedData.get(currentPk);
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
			}if(getPerfilFuncionalidadeMB() != null && getPerfilFuncionalidadeMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<PerfilFuncionalidadeView> perfilFuncionalidades = getDataProvider().getObjectByRange(
						getPerfilFuncionalidadeMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(perfilFuncionalidades != null && perfilFuncionalidades.size() > 0){
					for (PerfilFuncionalidadeView perfilFuncionalidade : perfilFuncionalidades) {
						wrappedKeys.add(perfilFuncionalidade.getIdPerfilFunc());
						wrappedData.put(perfilFuncionalidade.getIdPerfil(), perfilFuncionalidade);
						visitor.process(context, perfilFuncionalidade.getIdPerfilFunc(), argument);
					}
				}
			}
		}
	}
}
