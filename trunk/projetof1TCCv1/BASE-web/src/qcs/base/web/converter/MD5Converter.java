package qcs.base.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import qcs.webproject.security.Criptografia;

public class MD5Converter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(value != null && !value.isEmpty()){
			Criptografia crip = new Criptografia();
			return crip.criptografar(value);
		}
		return value;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		return object.toString();
	}

}
