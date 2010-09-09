package qcs.base.seguranca.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.mb.LovPessoaMB;
import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.base.seguranca.view.list.StatusUsuarioEnum;
import qcs.base.seguranca.web.dataprov.PerfilDataProvider;
import qcs.base.seguranca.web.dataprov.UsuarioDataProvider;
import qcs.base.web.aplicacao.mb.StatusAplicacao;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class UsuarioMB extends BaseMB {
	protected static Log log = LogFactory.getLog(UsuarioMB.class);
	private static final long serialVersionUID = 1L;

	private UsuarioDataProvider usuarioDataProvider;
	private PerfilDataProvider perfilDataProvider;
	private Usuario usuario;
	private UsuarioView view;
	private Map<String, Object> atributosFiltros;
	private LovPessoaMB lovPessoaMB;
	private StatusUsuarioEnum statusUsuarioEnum;

	//Filtros da tela
	private String login;
	private String cpfOuCnpj;
	private String nomePessoa;
	private String status;
	
	public void atualizarSelecao(){
		try{			
			usuario.setPessoa(
					getLovPessoaMB().getPessoaById());
						
			log.debug("Novo Pessoa: "+usuario.getPessoa());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{
			usuario.setPessoa(null);
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("login");
		atributosFiltros.remove("cpfOuCnpj");
		atributosFiltros.remove("nomePessoa");
		atributosFiltros.remove("status");
		
		atributosFiltros.put("login", login);
		atributosFiltros.put("cpfOuCnpj", cpfOuCnpj);
		atributosFiltros.put("nomePessoa", nomePessoa);
		atributosFiltros.put("status", status);
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDataProvider getUsuarioDataProvider() {
		if(usuarioDataProvider == null){
			usuarioDataProvider = (UsuarioDataProvider) getElResolver().getValue(getFacescontext().getELContext(), null, "usuarioDataProvider");
		}
		return usuarioDataProvider;
	}

	public void setUsuarioDataProvider(UsuarioDataProvider usuarioDataProvider) {
		this.usuarioDataProvider = usuarioDataProvider;
	}

	@Override
	public void adicionar() {
		try{
			if(usuario.getIdPerfil()!=null)
				usuario.setPerfil(
					perfilDataProvider.consultar(usuario.getIdPerfil()));
			
			usuario = getUsuarioDataProvider().incluir(usuario);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.usuario = null;
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.usuario = new Usuario();
			this.usuario.setAtivoBoolean(true);
			this.usuario.setBloqueadoBoolean(false);
			this.usuario.setDataAtivacao(new Date());
			this.usuario.setUsuarioAtiva(StatusAplicacao.getUsuarioLogadoNaSessao());
			this.usuario.setAdministradorBoolean(false);
			this.usuario.setAuditorBoolean(false);
			this.usuario.setMasterBoolean(false);
		}else{
			this.usuario = null;
		}
	}

	@Override
	public void editar() {
		try{
			if(usuario.getPerfil()!=null)
				usuario.setPerfil(
					perfilDataProvider.consultar(usuario.getPerfil().getIdPerfil()));
			this.usuario.setDataUltAlteracao(new Date());
			this.usuario.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			getUsuarioDataProvider().alterar(usuario);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {

		try{
			log.debug("Excluindo Usuário: "+usuario.getLogin());
			getUsuarioDataProvider().excluir(usuario);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Usuário: "+view.getLogin());
			usuario = getUsuarioDataProvider().consultar(view.getIdUsuario());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarEdicao(){	
		try{
			log.debug("Editando Usuário: "+view.getLogin());
			usuario = getUsuarioDataProvider().consultar(view.getIdUsuario());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarExclusao(){
		try{
			log.debug("Excluindo Usuário: "+view.getLogin());
			usuario = getUsuarioDataProvider().consultar(view.getIdUsuario());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
		
	}
	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return "Usuário: "+usuario.getLogin();
	}

	public UsuarioView getView() {
		return view;
	}

	public void setView(UsuarioView view) {
		this.view = view;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StatusUsuarioEnum getStatusUsuarioEnum() {
		return statusUsuarioEnum;
	}

	public void setStatusUsuarioEnum(StatusUsuarioEnum statusUsuarioEnum) {
		this.statusUsuarioEnum = statusUsuarioEnum;
	}
	
	public PerfilDataProvider getPerfilDataProvider() {
		return perfilDataProvider;
	}
	
	public void setPerfilDataProvider(PerfilDataProvider perfilDataProvider) {
		this.perfilDataProvider = perfilDataProvider;
	}
	
	public LovPessoaMB getLovPessoaMB() {
		return lovPessoaMB;
	}
	
	public void setLovPessoaMB(LovPessoaMB lovPessoaMB) {
		this.lovPessoaMB = lovPessoaMB;
	}
	
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}	
	
	public void ativar(){
		try{
			carregarEdicao();
			usuario.setAtivoBoolean(true);
			usuario.setDataUltAlteracao(new Date());
			usuario.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			getUsuarioDataProvider().alterar(usuario);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void desativar(){
		try{
			carregarEdicao();
			usuario.setAtivoBoolean(false);
			usuario.setDataUltAlteracao(new Date());
			usuario.setUsuarioAlt(StatusAplicacao.getUsuarioLogadoNaSessao());
			usuario.setDataDesativacao(new Date());
			usuario.setUsuarioDesativa(StatusAplicacao.getUsuarioLogadoNaSessao());
			getUsuarioDataProvider().alterar(usuario);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
}