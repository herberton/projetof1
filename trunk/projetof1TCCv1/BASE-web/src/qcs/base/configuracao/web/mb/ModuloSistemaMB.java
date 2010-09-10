package qcs.base.configuracao.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import qcs.base.configuracao.web.dataprov.ModuloSistemaDataProvider;
import qcs.base.lov.web.mb.LovModuloSistemaMB;
import qcs.base.modulo.ModuloSistema;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.ModuloSistemaView;

public class ModuloSistemaMB extends BaseMB {
	private static final long serialVersionUID = 1L;

	private ModuloSistemaDataProvider moduloSistemaDataProvider;
	private ModuloSistema moduloSistema;
	private LovModuloSistemaMB lovModuloSistemaMB;
	private ModuloSistemaView view;		
	private Long idModulo;
	private String nomeModulo;
	private Map<String, Object> atributosFiltros;

	public void atualizarSelecao(){
		try{
			moduloSistema.setModuloPai(
					getlovModuloSistemaMB().getModulo());
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			e.printStackTrace();
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void removerSelecao(){
		try{
			moduloSistema.setModuloPai(null);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public LovModuloSistemaMB getlovModuloSistemaMB() {
		if(lovModuloSistemaMB == null){
			lovModuloSistemaMB = (LovModuloSistemaMB) getElResolver().getValue(getFacescontext().getELContext(), null,"lovModuloSistemaMB");
		}
		return lovModuloSistemaMB;
	}

	public void setlovModuloSistemaMB(LovModuloSistemaMB lovModuloSistemaMB) {
		this.lovModuloSistemaMB = lovModuloSistemaMB;
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("idModulo");
		atributosFiltros.remove("nomeModulo");		
		atributosFiltros.put("idModulo",idModulo);
		atributosFiltros.put("nomeModulo",nomeModulo);

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}

	public ModuloSistema getModuloSistema() {
		return moduloSistema;
	}

	public void setModuloSistema(ModuloSistema moduloSistema) {
		this.moduloSistema = moduloSistema;
	}

	public ModuloSistemaView getView() {
		return view;
	}

	public void setView(ModuloSistemaView view) {
		this.view = view;
	}

	public ModuloSistemaDataProvider getModuloSistemaDataProvider() {
		if(moduloSistemaDataProvider == null){
			ELResolver resolver = FacesContext.getCurrentInstance().getApplication().getELResolver();  
			moduloSistemaDataProvider = (ModuloSistemaDataProvider) resolver.getValue(FacesContext.getCurrentInstance().getELContext(), null, "moduloSistemaDataProvider");
		}
		return moduloSistemaDataProvider;
	}

	public void setModuloSistemaDataProvider(ModuloSistemaDataProvider moduloSistemaDataProvider) {
		this.moduloSistemaDataProvider = moduloSistemaDataProvider;
	}

	@Override
	public void adicionar() {
		try{
			moduloSistema = getModuloSistemaDataProvider().incluir(moduloSistema);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		idModulo = null;
		nomeModulo = null;

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.moduloSistema = new ModuloSistema();
		}else{
			this.moduloSistema = null;
		}
	}

	@Override
	public void editar() {
		try{
			getModuloSistemaDataProvider().alterar(moduloSistema);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			getModuloSistemaDataProvider().excluir(moduloSistema);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			e.printStackTrace();
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
		// TODO Auto-generated method stub
	}

	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return moduloSistema.getNome();
	}

	public void carregarVisualizacao(){
		try{
			moduloSistema = getModuloSistemaDataProvider().consultar(view.getIdModulo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}			
	}

	public void carregarEdicao(){
		try{
			moduloSistema = getModuloSistemaDataProvider().consultar(view.getIdModulo());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			moduloSistema = getModuloSistemaDataProvider().consultar(view.getIdModulo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

}