package qcs.base.web.converter;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LongConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		String valor = (value==null || value.equals(""))?"":value.replace(" ", "");
		valor = valor.replace("-", "");
		if(valor.equals("")){
			return new Long(Long.MIN_VALUE);
		}else{
			if(!Pattern.matches("[a-zA-Z]", valor)){
				return new Long(valor);
			}else return new Long(Long.MIN_VALUE);
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		return (object == null || ((Long)object).longValue()== Long.MIN_VALUE )?"":object.toString();
	}

}
