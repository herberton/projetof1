package qcs.base.enderecamento.web.datamotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.base.enderecamento.web.dataprov.CidadeDataProvider;
import qcs.base.enderecamento.web.mb.CidadeMB;
import qcs.datamodel.GenericDataModel;

public class CidadeDataModel extends GenericDataModel<CidadeView, Cidade, Long> {
	protected static Log log = LogFactory.getLog(CidadeDataModel.class);
	private static final long serialVersionUID = 1L;
	private CidadeDataProvider dataProvider;
	private CidadeMB cidadeMB;

	public CidadeDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(CidadeDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public CidadeMB getCidadeMB() {
		return cidadeMB;
	}

	public void setCidadeMB(CidadeMB cidadeMB) {
		this.cidadeMB = cidadeMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nomeCidade";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getCidadeMB().getAtributosFiltros());
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
			}if(getCidadeMB() != null && getCidadeMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();

				List<CidadeView> cidades = getDataProvider().getObjectByRange(
						getCidadeMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(cidades != null && cidades.size() > 0){
					for (CidadeView cidade : cidades) {
						wrappedKeys.add(cidade.getIdCidade());
						wrappedData.put(cidade.getIdCidade(), cidade);
						visitor.process(context, cidade.getIdCidade(), argument);
					}
				}

			}else if(getCidadeMB() != null && getCidadeMB().isExecutarState()){
				for (Long key : wrappedKeys) {
					CidadeView cidadeView = wrappedData.get(key);
					cidadeView.setSelecionado(true);
					visitor.process(context, cidadeView.getIdCidade(), argument);
				}
			}
		}
	}

	public void selecionarTodos(){
		getCidadeMB().prepareExecutarAcao();
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
			CidadeView ret = wrappedData.get(currentPk);
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
