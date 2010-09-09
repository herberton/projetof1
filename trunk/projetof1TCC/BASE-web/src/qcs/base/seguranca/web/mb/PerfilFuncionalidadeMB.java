package qcs.base.seguranca.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.configuracao.web.dataprov.ModuloSistemaDataProvider;
import qcs.base.seguranca.PerfilFuncionalidade;
import qcs.base.seguranca.persistence.view.PerfilFuncionalidadeView;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.base.seguranca.web.dataprov.PerfilFuncionalidadeDataProvider;
import qcs.base.web.aplicacao.mb.StatusAplicacao;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class PerfilFuncionalidadeMB extends BaseMB {
	protected static Log log = LogFactory.getLog(PerfilFuncionalidadeMB.class);
	private static final long serialVersionUID = 1L;

	private PerfilFuncionalidadeDataProvider perfilFuncionalidadeDataProvider;
	private ModuloSistemaDataProvider moduloSistemaDataProvider;
	private PerfilFuncionalidade perfilFuncionalidade;
	private PerfilFuncionalidadeView view;
	private Map<String, Object> atributosFiltros;

	//FILTROS
	private Long idPerfilFunc;
	private Long idFuncionalidade;
	private String nomeFuncionalidade;
	private Long idPerfil;
	private String nomePerfil;
	private String ativo;
	private qcs.base.seguranca.web.dataprov.PerfilDataProvider perfilDataProvider;
	private qcs.base.configuracao.web.dataprov.FuncionalidadeDataProvider funcionalidadeDataProvider;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("idPerfilFunc");
		atributosFiltros.remove("idFuncionalidade");
		atributosFiltros.remove("nomeFuncionalidade");
		atributosFiltros.remove("idPerfil");
		atributosFiltros.remove("nomePerfil");
		atributosFiltros.remove("ativo");		
		atributosFiltros.put("idPerfilFunc",idPerfilFunc);
		atributosFiltros.put("idFuncionalidade",idFuncionalidade);		
		atributosFiltros.put("nomeFuncionalidade",nomeFuncionalidade);
		atributosFiltros.put("idPerfil",idPerfil);
		atributosFiltros.put("nomePerfil",nomePerfil);
		atributosFiltros.put("ativo",ativo);		
		return atributosFiltros;
	}


	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public PerfilFuncionalidade getPerfilFuncionalidade() {
		return perfilFuncionalidade;
	}

	public void setPerfilFuncionalidade(PerfilFuncionalidade perfilFuncionalidade) {
		this.perfilFuncionalidade = perfilFuncionalidade;
	}

	public PerfilFuncionalidadeDataProvider getPerfilFuncionalidadeDataProvider() {
//		if(perfilFuncionalidadeDataProvider == null){
//			perfilFuncionalidadeDataProvider = (PerfilFuncionalidadeDataProvider) 
//			getElResolver().getValue(getFacescontext().getELContext(), null, "perfilFuncionalidadeDataProvider");
//		}
		return perfilFuncionalidadeDataProvider;
	}

	public void setPerfilFuncionalidadeDataProvider(PerfilFuncionalidadeDataProvider perfilFuncionalidadeDataProvider) {
		this.perfilFuncionalidadeDataProvider = perfilFuncionalidadeDataProvider;
	}


	public PerfilFuncionalidadeView getView() {
		return view;
	}

	public void setView(PerfilFuncionalidadeView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo PerfilFuncionalidade: "+perfilFuncionalidade.getIdPerfilFunc());
			perfilFuncionalidade.setPerfil(getPerfilDataProvider().consultar(idPerfil));
			perfilFuncionalidade.setFuncionalidade(getFuncionalidadeDataProvider().consultar(idFuncionalidade));

			perfilFuncionalidade = getPerfilFuncionalidadeDataProvider().incluir(perfilFuncionalidade);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("PerfilFuncionalidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.idPerfilFunc = null;
		this.idFuncionalidade = null;
		this.nomeFuncionalidade = null;
		this.idPerfil = null;
		this.nomePerfil = null;
		this.ativo = null;;			

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.perfilFuncionalidade = new PerfilFuncionalidade();
			perfilFuncionalidade.setAtivo("S");
			perfilFuncionalidade.setDataAtivacao(new Date());
			perfilFuncionalidade.setUsuarioAtiva(StatusAplicacao.getUsuarioLogadoNaSessao());			
		}else{
			this.perfilFuncionalidade = null;
		}			
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando PerfilFuncionalidade: "+perfilFuncionalidade.getIdPerfilFunc());
			getPerfilFuncionalidadeDataProvider().alterar(perfilFuncionalidade);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("PerfilFuncionalidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo PerfilFuncionalidade: "+perfilFuncionalidade.getIdPerfilFunc());
			getPerfilFuncionalidadeDataProvider().excluir(perfilFuncionalidade);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("PerfilFuncionalidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
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
			return "Id:" + perfilFuncionalidade.getIdPerfilFunc()+ " Nome Perfil" + perfilFuncionalidade.getPerfil().getNomePerfil()+ " Nome Funcionalidade:" + perfilFuncionalidade.getFuncionalidade().getNome();
		}else if(isEditarState()){
			return "Id:" + view.getIdPerfilFunc()+ " Nome Perfil" + view.getNomePerfil()+ " Nome Funcionalidade:" + view.getNomeFuncionalidade();	
		}else if(isPesquisarState()){
			return "Id:" + perfilFuncionalidade.getIdPerfilFunc()+ " Nome Funcionalidade" + perfilFuncionalidade.getFuncionalidade().getNome()+ " Nome Perfil:" + perfilFuncionalidade.getPerfil().getNomePerfil();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando PerfilFuncionalidade: "+view.getIdPerfilFunc());
			perfilFuncionalidade = getPerfilFuncionalidadeDataProvider().consultar(view.getIdPerfilFunc());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando PerfilFuncionalidade: "+view.getIdPerfilFunc());
			perfilFuncionalidade = getPerfilFuncionalidadeDataProvider().consultar(view.getIdPerfilFunc());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo PerfilFuncionalidade: "+view.getIdPerfilFunc());
			perfilFuncionalidade = getPerfilFuncionalidadeDataProvider().consultar(view.getIdPerfilFunc());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "PerfilFuncionalidade: "+getTextoDocumento());
		}
	}
	
	
	
	public void ativar(){
		try{
			carregarEdicao();
			perfilFuncionalidade.setAtivo("S");
			getPerfilFuncionalidadeDataProvider().alterar(perfilFuncionalidade);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void desativar(){
		try{					
			carregarEdicao();
			perfilFuncionalidade.setAtivo("N");
			perfilFuncionalidade.setDataDesativacao(new Date());
			perfilFuncionalidade.setUsuarioDesativa(StatusAplicacao.getUsuarioLogadoNaSessao());
			getPerfilFuncionalidadeDataProvider().alterar(perfilFuncionalidade);			
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
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


	public Long getIdPerfilFunc() {
		return idPerfilFunc;
	}


	public void setIdPerfilFunc(Long idPerfilFunc) {
		this.idPerfilFunc = idPerfilFunc;
	}


	public Long getIdFuncionalidade() {
		return idFuncionalidade;
	}


	public void setIdFuncionalidade(Long idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}


	public String getNomeFuncionalidade() {
		return nomeFuncionalidade;
	}


	public void setNomeFuncionalidade(String nomeFuncionalidade) {
		this.nomeFuncionalidade = nomeFuncionalidade;
	}


	public Long getIdPerfil() {
		return idPerfil;
	}


	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}


	public String getNomePerfil() {
		return nomePerfil;
	}


	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}


	public String getAtivo() {
		return ativo;
	}


	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


	public qcs.base.seguranca.web.dataprov.PerfilDataProvider getPerfilDataProvider() {
		return perfilDataProvider;
	}


	public void setPerfilDataProvider(
			qcs.base.seguranca.web.dataprov.PerfilDataProvider perfilDataProvider) {
		this.perfilDataProvider = perfilDataProvider;
	}


	public qcs.base.configuracao.web.dataprov.FuncionalidadeDataProvider getFuncionalidadeDataProvider() {
		return funcionalidadeDataProvider;
	}


	public void setFuncionalidadeDataProvider(
			qcs.base.configuracao.web.dataprov.FuncionalidadeDataProvider funcionalidadeDataProvider) {
		this.funcionalidadeDataProvider = funcionalidadeDataProvider;
	}	
	
}