package qcs.base.web.aplicacao.mb.includes;

import java.util.ResourceBundle;

import manage.properties.ManageBundles;

import org.richfaces.component.html.HtmlPanelBar;

import qcs.webproject.mb.GenericDynamicMenuMB;

public class DynamicMenuMB extends GenericDynamicMenuMB{
	private HtmlPanelBar dynamicPanelBarSeguranca;
	private HtmlPanelBar dynamicPanelBarCadastrosBasicos;
	
	public HtmlPanelBar getDynamicPanelBarCadastrosBasicos() {
		if(dynamicPanelBarCadastrosBasicos == null){
			dynamicPanelBarCadastrosBasicos = createDynamicPanelBar(getConfFile().getString("nome_modulo_cad_basico"));
		}
		return dynamicPanelBarCadastrosBasicos;
	}

	public void setDynamicPanelBarCadastrosBasicos(
			HtmlPanelBar dynamicPanelBarCadastrosBasicos) {
		this.dynamicPanelBarCadastrosBasicos = dynamicPanelBarCadastrosBasicos;
	}

	public HtmlPanelBar getDynamicPanelBarSeguranca() {
		if(dynamicPanelBarSeguranca == null){
			dynamicPanelBarSeguranca = createDynamicPanelBar(getConfFile().getString("nome_modulo_seguranca"));
		}
		return dynamicPanelBarSeguranca;
	}

	public void setDynamicPanelBarSeguranca(
			HtmlPanelBar dynamicPanelBarSeguranca) {
		this.dynamicPanelBarSeguranca = dynamicPanelBarSeguranca;
	}

	public ResourceBundle getConfFile() {
		return ManageBundles.getConfMenu();
	}
}
