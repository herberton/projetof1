package qcs.base.tabelaauxiliar.web.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.view.ValorTabAuxView;
import qcs.base.tabelaauxiliar.web.dataprov.ValorTabAuxDataProvider;
import qcs.base.tabelaauxiliar.web.mb.ValorTabAuxMB;
import qcs.base.web.datamodel.GenericDataModel;

public class ValorTabAuxDataModel extends GenericDataModel<ValorTabAuxView, ValorTabAux, Long> {
	private static final long serialVersionUID = 1L;
	private ValorTabAuxDataProvider dataProvider;
	private ValorTabAuxMB valorTabAuxMB;

	public ValorTabAuxDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ValorTabAuxDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ValorTabAuxMB getValorTabAuxMB() {
		return valorTabAuxMB;
	}

	public void setValorTabAuxMB(ValorTabAuxMB valorTabAuxMB) {
		this.valorTabAuxMB = valorTabAuxMB;
	}

	@Override
	public String getDefaultSortField() {
		return "nome";
	}

	@Override
	public int getRowCount() {
		return getDataProvider().getRowCount(
				getValorTabAuxMB().getAtributosFiltros());
	}

	/**
	 * This is main part of Visitor pattern. Method called by framework many times during request processing. 
	 */
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException {	
		if (detached && getSortFieldObject() != null){   
			for (Long key : wrappedKeys){   
				setRowKey(key);   
				visitor.process(context, key, argument);   
			}
		}if(getValorTabAuxMB() != null && getValorTabAuxMB().isPesquisarState()){
			int firstRow = ((SequenceRange)range).getFirstRow();
			int numberOfRows = ((SequenceRange)range).getRows();
			wrappedKeys = new ArrayList<Long>();  


//			System.out.println(
//					"-walk-idTabaux:"+getValorTabAuxMB().getAtributosFiltros().get("idTabaux")+
//					"nome:"+getValorTabAuxMB().getAtributosFiltros().get("nome")+
//					"descricao:"+getValorTabAuxMB().getAtributosFiltros().get("descricao")
//			);

			List<ValorTabAuxView> valorTabAuxs = getDataProvider().getObjectByRange(
					getValorTabAuxMB().getAtributosFiltros(), new Integer(firstRow), numberOfRows, sortField, descending);


			if(valorTabAuxs != null && valorTabAuxs.size() > 0){
				for (ValorTabAuxView valorTabAuxView : valorTabAuxs) {
					wrappedKeys.add(valorTabAuxView.getIdValTabAux());
					wrappedData.put(valorTabAuxView.getIdValTabAux(), valorTabAuxView);
					visitor.process(context, valorTabAuxView.getIdValTabAux(), argument);
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
			ValorTabAuxView ret = wrappedData.get(currentPk);
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
