package qcs.base.web.aplicacao.mb;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocaleBean {   
	protected static Log log = LogFactory.getLog(LocaleBean.class);
	private HashMap<String, Locale> locales = null;
	private Locale localeAtual = null;
	protected static final String PROPERTIES_FILE_NAME = "idiomas"; 
	
	public LocaleBean() {   
		locales = new HashMap<String, Locale>(4);               
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.reinounido"),   
				new Locale("en", "UK"));   
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.eua"),   
				new Locale("en")); 
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.americasul"),   
				new Locale("es", "AR"));   
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.espanha"),   
				new Locale("es"));
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.alemanha"),  
				new Locale("de", "DE"));   
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.franca"),  
				new Locale("fr", "FR"));   
		locales.put(   
				ResourceBundle.getBundle(PROPERTIES_FILE_NAME).getString("idioma.brasil"),  
				new Locale("pt", "BR")); 
	}   

	public void chooseLocaleFromLink(ActionEvent event) {   
		//String current = event.getComponent().getId();   
		String current = (String)event.getComponent().getAttributes().get("value");   
		log.debug("Setando idioma "+current);
		setLocaleAtual((Locale)locales.get(current));
		FacesContext context = FacesContext.getCurrentInstance();   
		context.getViewRoot().setLocale(localeAtual); 
		log.debug("Idioma atual "+context.getViewRoot().getLocale());
	}

	public Locale getLocaleAtual() {
		return localeAtual;
	}

	public void setLocaleAtual(Locale localeAtual) {
		this.localeAtual = localeAtual;
	}   

}