package qcs.base.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class RGConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(value != null && !value.isEmpty()){
			value = value.replace(".", "");
		}
		return value;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		String rg = (String)object;
		String rgModificado = "";
		
		if(rg.length() == 8){
			rgModificado += rg.substring(0,2);
			rgModificado += "."+rg.substring(2,5);
			rgModificado += "."+rg.substring(5,8);
		}else return rg;
		
		return rgModificado;
	}

}
