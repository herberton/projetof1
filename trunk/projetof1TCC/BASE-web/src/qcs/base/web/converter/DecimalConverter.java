package qcs.base.web.converter;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class DecimalConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		String valor = (value==null || value.equals(""))?"":value.replace(" ", "");
		valor = valor.replace("-", "");
		if(valor.equals("")){
			return new Double(Double.MIN_VALUE);
		}else{
			if(!Pattern.matches("[a-zA-Z]", valor)){
				valor = valor.replace(",", ".");
				return new Double(valor);
			}else return new Double(Double.MIN_VALUE);
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		return (object == null || ((Double)object).doubleValue()== Double.MIN_VALUE )?"":object.toString();
	}

}
