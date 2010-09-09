package qcs.base.web.converter;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class IntegerConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		String valor = (value==null || value.equals(""))?"":value.replace(" ", "");
		if(valor.isEmpty()){
			return new Integer(Integer.MIN_VALUE);
		}else{
			if(!Pattern.matches("[a-zA-Z]", valor)){
				try{
					return new Integer(valor);
				}catch(Exception e){
					e.printStackTrace();
					return new Integer(Integer.MIN_VALUE);
				}
			}else return new Integer(Integer.MIN_VALUE);
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		return (object == null || ((Integer)object).intValue() == Integer.MIN_VALUE)?"":object.toString();
	}

}
