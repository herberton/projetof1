package qcs.base.seguranca.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.base.seguranca.web.dataprov.PerfilDataProvider;
import qcs.base.web.aplicacao.mb.StatusAplicacao;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class PerfilMB extends BaseMB {
	protected static Log log = LogFactory.getLog(PerfilMB.class);
	private static final long serialVersionUID = 1L;

	private PerfilDataProvider perfilDataProvider;
	private Perfil perfil;
	private PerfilView view;
	private Map<String, Object> atributosFiltros;

	private String nomePerfilAcesso;
	private String descPerfilAcesso;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("nomePerfilAcesso");
		atributosFiltros.remove("descPerfilAcesso");
		atributosFiltros.put("nomePerfilAcesso",nomePerfilAcesso);
		atributosFiltros.put("descPerfilAcesso",descPerfilAcesso);		
		return atributosFiltros;
	}

	public String getNomePerfilAcesso() {
		return nomePerfilAcesso;
	}

	public void setNomePerfilAcesso(String nomePerfilAcesso) {
		this.nomePerfilAcesso = nomePerfilAcesso;
	}

	public String getDescPerfilAcesso() {
		return descPerfilAcesso;
	}

	public void setDescPerfilAcesso(String descPerfilAcesso) {
		this.descPerfilAcesso = descPerfilAcesso;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public PerfilDataProvider getPerfilDataProvider() {
		log.debug("PerfilMB : getPerfilDataProvider");
		if(perfilDataProvider == null){
			perfilDataProvider = (PerfilDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "perfilDataProvider");
		}
		return perfilDataProvider;
	}

	public void setPerfilDataProvider(PerfilDataProvider perfilDataProvider) {
		this.perfilDataProvider = perfilDataProvider;
	}

	
	public PerfilView getView() {
		return view;
	}

	public void setView(PerfilView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			perfil = getPerfilDataProvider().incluir(perfil);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nomePerfilAcesso = "";
		this.descPerfilAcesso = "";
		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.perfil = new Perfil();
			this.perfil.setDataAtivacao(new Date());
			this.perfil.setAtivoTrans(true);
			this.perfil.setUsuarioAtiva(StatusAplicacao.getUsuarioLogadoNaSessao());
		}else{
			this.perfil = null;
		}
	}

	@Override
	public void editar() {
		try{
			perfil.setDataUltAlteracao(new Date());
			perfil.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			getPerfilDataProvider().alterar(perfil);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void ativar(){
		try{
			carregarEdicao();
			perfil.setAtivoTrans(true);
			perfil.setDataUltAlteracao(new Date());
			perfil.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			getPerfilDataProvider().alterar(perfil);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void desativar(){
		try{					
			carregarEdicao();
			perfil.setAtivoTrans(false);
			perfil.setDataUltAlteracao(new Date());
			perfil.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			perfil.setDataDesativacao(new Date());
			perfil.setUsuarioDesativa(StatusAplicacao.getUsuarioLogadoNaSessao());
			getPerfilDataProvider().alterar(perfil);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			getPerfilDataProvider().excluir(perfil);
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
		return "Perfil: Id:" + view.getIdPerfil()+ " Perfil" + view.getNomePerfilAcesso()+ " Descrição do Perfil:" + view.getDescPerfilAcesso();
	}
	
	public void carregarVisualizacao(){
		try{
			perfil = getPerfilDataProvider().consultar(view.getIdPerfil());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarEdicao(){
		try{
			perfil = getPerfilDataProvider().consultar(view.getIdPerfil());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarExclusao(){
		try{
			perfil = getPerfilDataProvider().consultar(view.getIdPerfil());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	
	public String carregarFuncionalidades(){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("perfilView", view);
		
		return "funcionalidade";
	}		
}