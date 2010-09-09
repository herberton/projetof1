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

import qcs.base.enderecamento.Logradouro;
import qcs.base.enderecamento.persistence.view.LogradouroView;
import qcs.base.enderecamento.web.dataprov.LogradouroDataProvider;
import qcs.base.enderecamento.web.mb.LogradouroMB;
import qcs.datamodel.GenericDataModel;

public class LogradouroDataModel extends GenericDataModel<LogradouroView, Logradouro, Long> {
	protected static Log log = LogFactory.getLog(LogradouroDataModel.class);
	private static final long serialVersionUID = 1L;
	private LogradouroDataProvider dataProvider;
	private LogradouroMB logradouroMB;

	public LogradouroDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(LogradouroDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public LogradouroMB getLogradouroMB() {
		return logradouroMB;
	}

	public void setLogradouroMB(LogradouroMB logradouroMB) {
		this.logradouroMB = logradouroMB;
	}

	@Override
	public String getDefaultSortField() {
		return "cep";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getLogradouroMB().getAtributosFiltros());
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
			}if(getLogradouroMB() != null && getLogradouroMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();

				List<LogradouroView> logradouros = getDataProvider().getObjectByRange(
						getLogradouroMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(logradouros != null && logradouros.size() > 0){
					for (LogradouroView logradouro : logradouros) {
						wrappedKeys.add(logradouro.getIdLogradouro());
						wrappedData.put(logradouro.getIdLogradouro(), logradouro);
						visitor.process(context, logradouro.getIdLogradouro(), argument);
					}
				}
			}
		}
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
			LogradouroView ret = wrappedData.get(currentPk);
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
