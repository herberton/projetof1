package qcs.base.seguranca.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.base.seguranca.web.dataprov.UsuarioDataProvider;
import qcs.base.seguranca.web.mb.UsuarioMB;
import qcs.base.web.datamodel.GenericDataModel;

public class UsuarioDataModel extends GenericDataModel<UsuarioView, Usuario, Long> {
	protected static Log log = LogFactory.getLog(UsuarioDataModel.class);
	private static final long serialVersionUID = 1L;
	private UsuarioDataProvider dataProvider;
	private UsuarioMB usuarioMB;

	public UsuarioDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(UsuarioDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	@Override
	public String getDefaultSortField() {
		return "login";
	}

	@Override
	public int getRowCount() {
		try{
			return getDataProvider().getRowCount(getUsuarioMB().getAtributosFiltros());
		}catch(Exception e){
			log.debug("Erro UsuarioDataModel : getRowCount", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro UsuarioDataModel : getRowCount", e.getMessage()));
		}
		return 0;
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
			UsuarioView ret = wrappedData.get(currentPk);
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
		try{
			if(getJSF_FASE().startsWith("RENDER_RESPONSE")){
				if (detached && getSortFieldObject() != null){   
					for (Long key : wrappedKeys){   
						setRowKey(key);   
						visitor.process(context, key, argument);   
					}
				}if(getUsuarioMB() != null && getUsuarioMB().isPesquisarState()){
					int firstRow = ((SequenceRange)range).getFirstRow();
					int numberOfRows = ((SequenceRange)range).getRows();
					wrappedKeys = new ArrayList<Long>();

					List<UsuarioView> usuarios = getDataProvider().getObjectByRange(
							getUsuarioMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

					if(usuarios != null && usuarios.size() > 0){
						for (UsuarioView usuarioView : usuarios) {
							wrappedKeys.add(usuarioView.getIdUsuario());
							wrappedData.put(usuarioView.getIdUsuario(), usuarioView);
							visitor.process(context, usuarioView.getIdUsuario(), argument);
						}
					}
				}
			}
		}catch(Exception e){
			log.debug("Erro UsuarioDataModel : walk", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro UsuarioDataModel : walk", e.getMessage()));
		}
	}
}
