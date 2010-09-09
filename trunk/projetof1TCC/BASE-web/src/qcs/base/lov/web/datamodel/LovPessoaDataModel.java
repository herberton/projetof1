package qcs.base.lov.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.lov.web.dataprov.LovPessoaDataProvider;
import qcs.base.lov.web.mb.LovPessoaMB;
import qcs.base.pessoa.persistence.view.LovPessoaView;
import qcs.base.web.datamodel.GenericDataModel;

public class LovPessoaDataModel extends GenericDataModel<LovPessoaView, LovPessoaView, Long> {
	private static final long serialVersionUID = 1L;
	private LovPessoaDataProvider dataProvider;
	private LovPessoaMB lovPessoaMB;

	public LovPessoaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(LovPessoaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public String getDefaultSortField() {
		return "nomePessoa";
	}

	@Override
	public int getRowCount() {
		try{
			return getDataProvider().getRowCount(getLovPessoaMB().getAtributosFiltros());
		}catch(Exception e){
			log.debug("Erro LovPessoaDataModel : getRowCount", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovPessoaDataModel : getRowCount", e.getMessage()));
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
			LovPessoaView lovPessoaView = wrappedData.get(currentPk);
			if (lovPessoaView==null) {
				lovPessoaView = getDataProvider().getObjectByPk(currentPk);
				wrappedData.put(currentPk, lovPessoaView);
				return lovPessoaView;
			} else {
				return lovPessoaView;
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
//			if(getJSF_FASE().startsWith("RENDER_RESPONSE")){		
				if (detached && getSortFieldObject() != null){   
					for (Long key : wrappedKeys){   
						setRowKey(key);   
						visitor.process(context, key, argument);   
					}
				}if(getLovPessoaMB() != null){
					int firstRow = ((SequenceRange)range).getFirstRow();
					int numberOfRows = ((SequenceRange)range).getRows();
					wrappedKeys = new ArrayList<Long>(); 

					List<LovPessoaView> lovPessoas = getDataProvider().getObjectByRange(
							getLovPessoaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

					if(lovPessoas != null && lovPessoas.size() > 0){
						for (LovPessoaView lovPessoaView : lovPessoas) {
							wrappedKeys.add(lovPessoaView.getIdPessoa());
							wrappedData.put(lovPessoaView.getIdPessoa(), lovPessoaView);
							visitor.process(context, lovPessoaView.getIdPessoa(), argument);
						}
					}
				}
//			}
		}catch(Exception e){
			log.debug("Erro LovPessoaDataModel : walk", e);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro LovPessoaDataModel : walk", e.getMessage()));
		}
	}

	public LovPessoaMB getLovPessoaMB() {
		return lovPessoaMB;
	}

	public void setLovPessoaMB(LovPessoaMB lovPessoaMB) {
		this.lovPessoaMB = lovPessoaMB;
	}

}
