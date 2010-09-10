package qcs.base.seguranca.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.seguranca.PerfilUsuario;
import qcs.base.seguranca.persistence.view.PerfilUsuarioView;
import qcs.base.seguranca.web.dataprov.PerfilUsuarioDataProvider;
import qcs.base.seguranca.web.mb.PerfilUsuarioMB;
import qcs.base.web.datamodel.GenericDataModel;

public class PerfilUsuarioDataModel extends GenericDataModel<PerfilUsuarioView, PerfilUsuario, Long> {
	private static final long serialVersionUID = 1L;
	private PerfilUsuarioDataProvider dataProvider;
	private PerfilUsuarioMB perfilUsuarioMB;

	public PerfilUsuarioDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PerfilUsuarioDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public PerfilUsuarioMB getPerfilUsuarioMB() {
		return perfilUsuarioMB;
	}

	public void setPerfilUsuarioMB(PerfilUsuarioMB perfilUsuarioMB) {
		this.perfilUsuarioMB = perfilUsuarioMB;
	}

	@Override
	public String getDefaultSortField() {
		return "dataUltAlteracao";
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
			PerfilUsuarioView ret = wrappedData.get(currentPk);
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
			}if(getPerfilUsuarioMB() != null && getPerfilUsuarioMB().isPesquisarState()){
				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<PerfilUsuarioView> perfils = getDataProvider().getObjectByRange(
						getPerfilUsuarioMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(perfils != null && perfils.size() > 0){
					for (PerfilUsuarioView perfil : perfils) {
						wrappedKeys.add(perfil.getIdPerfilUsuario());
						wrappedData.put(perfil.getIdPerfilUsuario(), perfil);
						visitor.process(context, perfil.getIdPerfilUsuario(), argument);
					}
				}
			}
		}

	}
}
