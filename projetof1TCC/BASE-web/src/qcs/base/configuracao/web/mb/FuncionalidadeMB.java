package qcs.base.configuracao.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.configuracao.web.dataprov.FuncionalidadeDataProvider;
import qcs.base.configuracao.web.dataprov.ModuloSistemaDataProvider;
import qcs.base.modulo.Funcionalidade;
import qcs.base.modulo.ModuloSistema;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.FuncionalidadeView;

public class FuncionalidadeMB extends BaseMB {
	protected static Log log = LogFactory.getLog(FuncionalidadeMB.class);
	private static final long serialVersionUID = 1L;

	private FuncionalidadeDataProvider funcionalidadeDataProvider;
	private ModuloSistemaDataProvider moduloSistemaDataProvider;
	private Funcionalidade funcionalidade;
	private FuncionalidadeView view;
	private Map<String, Object> atributosFiltros;

	private String nomeFuncionalidade;
	private String visivel;
	private Long idMod;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		
		atributosFiltros.remove("idPerfil");
		if(getPerfilView() != null)
			atributosFiltros.put("idPerfil",getPerfilView().getIdPerfil());
		atributosFiltros.remove("nomeFuncionalidade");
		atributosFiltros.put("nomeFuncionalidade",nomeFuncionalidade);
		atributosFiltros.remove("visivel");
		atributosFiltros.put("visivel", visivel);
		atributosFiltros.remove("idMod");
		atributosFiltros.put("idMod", idMod);
		
		return atributosFiltros;
	}

	public String getNomeFuncionalidade() {
		return nomeFuncionalidade;
	}

	public void setNomeFuncionalidade(String nomeFuncionalidade) {
		this.nomeFuncionalidade = nomeFuncionalidade;
	}

	public void setNomeFuncionalidadeAcesso(String nomeFuncionalidadeAcesso) {
		this.nomeFuncionalidade = nomeFuncionalidadeAcesso;
	}


	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public FuncionalidadeDataProvider getFuncionalidadeDataProvider() {
		if(funcionalidadeDataProvider == null){
			funcionalidadeDataProvider = (FuncionalidadeDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "funcionalidadeDataProvider");
		}
		return funcionalidadeDataProvider;
	}

	public void setFuncionalidadeDataProvider(FuncionalidadeDataProvider funcionalidadeDataProvider) {
		this.funcionalidadeDataProvider = funcionalidadeDataProvider;
	}


	public FuncionalidadeView getView() {
		return view;
	}

	public void setView(FuncionalidadeView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Funcionalidade: "+funcionalidade.getIdFunc());
			log.debug("Módulo: "+getFuncionalidade().getModuloSistema().getIdModulo());
			getFuncionalidade().setModuloSistema(
					getModuloSistemaDataProvider().consultar(
							getFuncionalidade().getModuloSistema().getIdModulo())
			);
			funcionalidade = getFuncionalidadeDataProvider().incluir(funcionalidade);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nomeFuncionalidade = "";

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.funcionalidade = new Funcionalidade();
			funcionalidade.setModuloSistema(new ModuloSistema());
		}else{
			this.funcionalidade = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando Funcionalidade: "+funcionalidade.getIdFunc());
			getFuncionalidadeDataProvider().alterar(funcionalidade);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Funcionalidade: "+funcionalidade.getIdFunc());
			getFuncionalidadeDataProvider().excluir(funcionalidade);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
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
		if(isAdicionarState()){
			return "Id:" + funcionalidade.getIdFunc()+ " Nome Funcionalidade:" + funcionalidade.getNome()+ " Descrição do Funcionalidade:" + funcionalidade.getDescricao();
		}else if(isEditarState()){
			return "Id:" + view.getIdFuncionalidade()+ " Nome Funcionalidade:" + view.getNomeFuncionalidade()+ " Descrição do Funcionalidade:" + view.getDescricaoFuncionalidade();	
		}else if(isPesquisarState()){
			return "Id:" + funcionalidade.getIdFunc()+ " Nome Funcionalidade:" + funcionalidade.getNome()+ " Descrição do Funcionalidade:" + funcionalidade.getDescricao();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Funcionalidade: "+view.getIdFuncionalidade());
			funcionalidade = getFuncionalidadeDataProvider().consultar(view.getIdFuncionalidade());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Funcionalidade: "+view.getIdFuncionalidade());
			funcionalidade = getFuncionalidadeDataProvider().consultar(view.getIdFuncionalidade());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Funcionalidade: "+view.getIdFuncionalidade());
			funcionalidade = getFuncionalidadeDataProvider().consultar(view.getIdFuncionalidade());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public PerfilView getPerfilView(){	
		return (PerfilView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("perfilView");
	}

	public ModuloSistemaDataProvider getModuloSistemaDataProvider() {
		if(moduloSistemaDataProvider == null){
			moduloSistemaDataProvider = (ModuloSistemaDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "moduloSistemaDataProvider");
		}
		return moduloSistemaDataProvider;
	}

	public void setModuloSistemaDataProvider(
			ModuloSistemaDataProvider moduloSistemaDataProvider) {
		this.moduloSistemaDataProvider = moduloSistemaDataProvider;
	}

	public String getVisivel() {
		return visivel;
	}

	public void setVisivel(String visivel) {
		this.visivel = visivel;
	}

	public Long getIdMod() {
		return idMod;
	}

	public void setIdMod(Long idMod) {
		this.idMod = idMod;
	}
	
}