package qcs.base.lov.web.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.configuracao.web.dataprov.ModuloSistemaDataProvider;
import qcs.base.modulo.ModuloSistema;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class LovModuloSistemaMB implements Serializable{
	protected static Log log = LogFactory.getLog(LovModuloSistemaMB.class);
	private static final long serialVersionUID = 1L;

	private ModuloSistemaDataProvider dataProvider;
	private Map<String, Object> atributosFiltros;
	private String exibir;
	private Long idModuloSistemaSelecionado;
	
	//Filtro da Lov
	private String nomeModulo;
	
	public ModuloSistema getModulo() throws InfrastructureException, Exception{
		return getDataProvider().consultar(getIdModuloSistemaSelecionado());
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
			atributosFiltros.remove("nomeModulo");
			atributosFiltros.put("nomeModulo", nomeModulo);
		
		return atributosFiltros;
	}
	

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Long getIdModuloSistemaSelecionado() {
		return idModuloSistemaSelecionado;
	}

	public void setIdModuloSistemaSelecionado(Long idModuloSistemaSelecionado) {
		this.idModuloSistemaSelecionado = idModuloSistemaSelecionado;
	}

	public ModuloSistemaDataProvider getDataProvider() {
		if(dataProvider == null){
			ELResolver resolver = FacesContext.getCurrentInstance().getApplication().getELResolver();  
			dataProvider = (ModuloSistemaDataProvider) resolver.getValue(FacesContext.getCurrentInstance().getELContext(), null, "moduloSistemaDataProvider");
		}
		return dataProvider;
	}

	public void setDataProvider(ModuloSistemaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	public ModuloSistema getModuloSistema() throws InfrastructureException, Exception{
		return getDataProvider().consultar(getIdModuloSistemaSelecionado());
	}

	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}
	
	public void limpar(){
		this.nomeModulo = "";
	}
	
}