package qcs.base.seguranca.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.base.seguranca.web.dataprov.PerfilDataProvider;
import qcs.base.seguranca.web.mb.PerfilMB;
import qcs.base.web.datamodel.GenericDataModel;

public class PerfilDataModel extends GenericDataModel<PerfilView, Perfil, Long> {
	private static final long serialVersionUID = 1L;
	private PerfilDataProvider dataProvider;
	private PerfilMB perfilMB;

	public PerfilDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PerfilDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public PerfilMB getPerfilMB() {
		return perfilMB;
	}

	public void setPerfilMB(PerfilMB perfilMB) {
		this.perfilMB = perfilMB;
	}

	@Override
	public String getDefaultSortField() {
		return "idPerfil";
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
			PerfilView ret = wrappedData.get(currentPk);
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
			}if(getPerfilMB() != null && getPerfilMB().isPesquisarState()){
				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<PerfilView> perfils = getDataProvider().getObjectByRange(
						getPerfilMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(perfils != null && perfils.size() > 0){
					for (PerfilView perfil : perfils) {
						wrappedKeys.add(perfil.getIdPerfil());
						wrappedData.put(perfil.getIdPerfil(), perfil);
						visitor.process(context, perfil.getIdPerfil(), argument);
					}
				}
			}
		}

	}
}
