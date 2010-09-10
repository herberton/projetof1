package qcs.base.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CEPConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(value != null && !value.isEmpty()){
			value = value.replace("-", "");
		}
		return value;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if(object == null) return "";
		String cep = (String)object;
		StringBuilder cepModificado = new StringBuilder();
		if(cep.length() == 8){
			cepModificado.append(cep.substring(0, 5));
			cepModificado.append("-");
			cepModificado.append(cep.substring(5));
			System.out.println("retorno cep:"+cep);
		}else return cep;
		return cepModificado.toString();
	}

}
