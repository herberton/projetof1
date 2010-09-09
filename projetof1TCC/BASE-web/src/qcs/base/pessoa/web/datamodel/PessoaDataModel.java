package qcs.base.pessoa.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.persistence.view.PessoaView;
import qcs.base.pessoa.web.dataprov.PessoaDataProvider;
import qcs.base.pessoa.web.mb.PessoaMB;
import qcs.datamodel.GenericDataModel;

public class PessoaDataModel extends GenericDataModel<PessoaView, Pessoa, Long> {
	protected static Log log = LogFactory.getLog(PessoaDataModel.class);
	private static final long serialVersionUID = 1L;
	private PessoaDataProvider dataProvider;
	private PessoaMB pessoaMB;

	public PessoaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PessoaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public PessoaMB getPessoaMB() {
		return pessoaMB;
	}

	public void setPessoaMB(PessoaMB pessoaMB) {
		this.pessoaMB = pessoaMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nomePessoa";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(getPessoaMB().getAtributosFiltros());
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
			}if(getPessoaMB() != null && getPessoaMB().isPesquisarState()){

				int firstRow = ((SequenceRange)range).getFirstRow();
				int numberOfRows = ((SequenceRange)range).getRows();
				wrappedKeys = new ArrayList<Long>();

				List<PessoaView> pessoas = getDataProvider().getObjectByRange(
						getPessoaMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);

				if(pessoas != null && pessoas.size() > 0){
					for (PessoaView pessoa : pessoas) {
						wrappedKeys.add(pessoa.getIdPessoa());
						wrappedData.put(pessoa.getIdPessoa(), pessoa);
						visitor.process(context, pessoa.getIdPessoa(), argument);
					}
				}

			}else if(getPessoaMB() != null && getPessoaMB().isExecutarState()){
				for (Long key : wrappedKeys) {
					PessoaView pessoaView = wrappedData.get(key);
					visitor.process(context, pessoaView.getIdPessoa(), argument);
				}
			}
		}
	}

	public void selecionarTodos(){
		getPessoaMB().prepareExecutarAcao();
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
			PessoaView ret = wrappedData.get(currentPk);
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
