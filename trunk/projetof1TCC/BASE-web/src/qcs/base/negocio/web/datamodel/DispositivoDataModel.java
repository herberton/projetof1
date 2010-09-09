package qcs.base.negocio.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.web.dataprov.DispositivoDataProvider;
import qcs.base.negocio.web.mb.DispositivoMB;
import qcs.base.web.datamodel.GenericDataModel;
import qcs.persistence.rhdefensoria.view.DispositivoView;

public class DispositivoDataModel extends GenericDataModel<DispositivoView, Dispositivo, Long> {

	private static final long serialVersionUID = 1L;
	private DispositivoMB dispositivoMB;
	private DispositivoDataProvider dataProvider;
	
	
	//CONSTRUTOR...
	public DispositivoDataModel() { super(); }
	
	
	//GET...
	public DispositivoMB getDispositivoMB() {
		return dispositivoMB;
	}
	public DispositivoDataProvider getDataProvider() {
		return dataProvider;
	}
	
	
	//SET...
	public void setDispositivoMB(DispositivoMB dispositivoMB) {
		this.dispositivoMB = dispositivoMB;
	}
	public void setDataProvider(
			DispositivoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	
	//MÉTODOS...
	@Override
	public String getDefaultSortField() {
		return "ip";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getDispositivoMB().getAtributosFiltros());
	}

	@Override
	public Object getRowData() {
		if (currentPk==null) {
			return null;
		} else {
			DispositivoView ret = wrappedData.get(currentPk);
			if (ret==null) {
				ret = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, ret);
				return ret;
			} else {
				return ret;
			}
		}
	}

	@Override
	public boolean isRowAvailable() {
		if (currentPk==null) {
			return false;
		} else {
			return getDataProvider().hasObjectByPk(currentPk);
		}
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {
		if(getJSF_FASE().startsWith("RENDER_RESPONSE")){

			if (detached && getSortFieldObject() != null){   
				for (Long key : wrappedKeys){   
					setRowKey(key);   
					visitor.process(context, key, argument);   
				}
			}if(getDispositivoMB() != null && getDispositivoMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();  

				List<DispositivoView> viewList = getDataProvider().getObjectByRange(
						getDispositivoMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(viewList != null && viewList.size() > 0){
					for (DispositivoView view : viewList) {
						wrappedKeys.add(view.getIdDispositivo());
						wrappedData.put(view.getIdDispositivo(), view);
						visitor.process(context, view.getIdDispositivo(), argument);
					}
				}
			}
		}
	}

}
