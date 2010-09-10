package qcs.base.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateConverter implements Converter {
	protected static Log log = LogFactory.getLog(DateConverter.class);

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if(value != null && !value.isEmpty()){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return format.parse(value);
			} catch (ParseException e) {
				log.error("Erro ao converter objeto", e);
				return null;
			}
		}else{
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if(object == null) return "";
		if(object instanceof Calendar){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format(((Calendar)object).getTime());
		}else if(object instanceof Date){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			return format.format((Date)object);
		}else{
			log.debug("Erro ao converter objeto :"+object);
			return "";
		}

	}

}
