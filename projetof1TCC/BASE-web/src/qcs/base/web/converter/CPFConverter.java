package qcs.base.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CPFConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		return value;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		String cpf = (String)object;
		String cpfModificado = "";
		if(cpf.length() == 11){
			cpfModificado += cpf.substring(0,3);
			cpfModificado += "."+cpf.substring(3,6);
			cpfModificado += "."+cpf.substring(6,9);
			cpfModificado += "-"+cpf.substring(9,11);
		}else return cpf;
		return cpfModificado;
	}

}
