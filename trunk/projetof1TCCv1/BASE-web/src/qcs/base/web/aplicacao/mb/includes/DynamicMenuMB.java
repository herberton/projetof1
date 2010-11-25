package qcs.base.web.aplicacao.mb.includes;

import java.util.ResourceBundle;

import manage.properties.ManageBundles;

import org.richfaces.component.html.HtmlPanelBar;

import qcs.webproject.mb.GenericDynamicMenuMB;

public class DynamicMenuMB extends GenericDynamicMenuMB{
	private HtmlPanelBar dynamicPanelBarPortaria;
	private HtmlPanelBar dynamicPanelBarCadastro;	

	public HtmlPanelBar getDynamicPanelBarPortaria() {
		if(dynamicPanelBarPortaria == null){
			dynamicPanelBarPortaria = createDynamicPanelBar(getConfFile().getString("nome_modulo_portaria"));
		}
		return dynamicPanelBarPortaria;
	}

	public void setDynamicPanelBarPortaria(
			HtmlPanelBar dynamicPanelBarPortaria) {
		this.dynamicPanelBarPortaria = dynamicPanelBarPortaria;
	}	
	
	public HtmlPanelBar getdynamicPanelBarCadastro() {
		if(dynamicPanelBarCadastro == null){
			dynamicPanelBarCadastro = createDynamicPanelBar(getConfFile().getString("nome_modulo_cadastro"));
		}
		return dynamicPanelBarCadastro;
	}

	public void setdynamicPanelBarCadastro(
			HtmlPanelBar dynamicPanelBarCadastro) {
		this.dynamicPanelBarCadastro = dynamicPanelBarCadastro;
	}		
	
	public ResourceBundle getConfFile() {
		return ManageBundles.getConfMenu();
	}
}
