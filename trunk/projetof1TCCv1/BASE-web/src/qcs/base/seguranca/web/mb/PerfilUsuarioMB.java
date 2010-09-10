package qcs.base.seguranca.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.PerfilUsuario;
import qcs.base.seguranca.persistence.view.PerfilUsuarioView;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.base.seguranca.web.dataprov.PerfilUsuarioDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class PerfilUsuarioMB extends BaseMB {
	protected static Log log = LogFactory.getLog(PerfilUsuarioMB.class);
	private static final long serialVersionUID = 1L;

	private PerfilUsuarioDataProvider perfilUsuarioDataProvider;
	private PerfilUsuario perfilUsuario;
	private PerfilUsuarioView view;
	private Map<String, Object> atributosFiltros;

	//Filtros da tela
	private Long idPerfil;
	private String usuario;
	private Date dataAtivaInicial;
	private Date dataAtivaFinal;
	private Date dataDesativaInicial;
	private Date dataDesativaFinal;
	private Date dataUltInicial;
	private Date dataUltFinal;
	
	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
	
		atributosFiltros.remove("idPerfil");
		atributosFiltros.put("idPerfil", idPerfil);
		atributosFiltros.remove("usuario");
		atributosFiltros.put("usuario", usuario);
		atributosFiltros.remove("dataAtivaInicial");
		atributosFiltros.put("dataAtivaInicial", dataAtivaInicial);
		atributosFiltros.remove("dataAtivaFinal");
		atributosFiltros.put("dataAtivaFinal", dataAtivaFinal);
		atributosFiltros.remove("dataDesativaInicial");
		atributosFiltros.put("dataDesativaInicial", dataDesativaInicial);
		atributosFiltros.remove("dataDesativaFinal");
		atributosFiltros.put("dataDesativaFinal", dataDesativaFinal);
		atributosFiltros.remove("dataUltInicial");
		atributosFiltros.put("dataUltInicial", dataUltInicial);
		atributosFiltros.remove("dataUltFinal");
		atributosFiltros.put("dataUltFinal", dataUltFinal);	
		
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public PerfilUsuarioDataProvider getPerfilUsuarioDataProvider() {
		log.debug("PerfilMB : getPerfilDataProvider");
		if(perfilUsuarioDataProvider == null){
			perfilUsuarioDataProvider = (PerfilUsuarioDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "perfilUsuarioDataProvider");
		}
		return perfilUsuarioDataProvider;
	}

	public void setPerfilUsuarioDataProvider(PerfilUsuarioDataProvider perfilUsuarioDataProvider) {
		this.perfilUsuarioDataProvider = perfilUsuarioDataProvider;
	}

	

	@Override
	public void adicionar() {
		try{
			perfilUsuario = getPerfilUsuarioDataProvider().incluir(perfilUsuario);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.perfilUsuario = new PerfilUsuario();
		}else{
			this.perfilUsuario = null;
		}
	}

	@Override
	public void editar() {
		try{
			getPerfilUsuarioDataProvider().alterar(perfilUsuario);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			getPerfilUsuarioDataProvider().excluir(perfilUsuario);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	public void carregarVisualizacao(){
		try{
			log.debug("Visualizando Perfil Usuario: "+view.getIdPerfilUsuario());
			perfilUsuario = getPerfilUsuarioDataProvider().consultar(view.getIdPerfilUsuario());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	
	@Override
	public void salvar() {
		// TODO Auto-generated method stub
		
	}	
	
	public String getTextoDocumento(){
		return "Id:" + view.getIdPerfilUsuario();
	}

	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public PerfilUsuarioView getView() {
		return view;
	}

	public void setView(PerfilUsuarioView view) {
		this.view = view;
	}
	
	public PerfilView getPerfilView(){	
		return (PerfilView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("perfilView");
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getDataAtivaInicial() {
		return dataAtivaInicial;
	}

	public void setDataAtivaInicial(Date dataAtivaInicial) {
		this.dataAtivaInicial = dataAtivaInicial;
	}

	public Date getDataAtivaFinal() {
		return dataAtivaFinal;
	}

	public void setDataAtivaFinal(Date dataAtivaFinal) {
		this.dataAtivaFinal = dataAtivaFinal;
	}

	public Date getDataDesativaInicial() {
		return dataDesativaInicial;
	}

	public void setDataDesativaInicial(Date dataDesativaInicial) {
		this.dataDesativaInicial = dataDesativaInicial;
	}

	public Date getDataDesativaFinal() {
		return dataDesativaFinal;
	}

	public void setDataDesativaFinal(Date dataDesativaFinal) {
		this.dataDesativaFinal = dataDesativaFinal;
	}

	public Date getDataUltInicial() {
		return dataUltInicial;
	}

	public void setDataUltInicial(Date dataUltInicial) {
		this.dataUltInicial = dataUltInicial;
	}

	public Date getDataUltFinal() {
		return dataUltFinal;
	}

	public void setDataUltFinal(Date dataUltFinal) {
		this.dataUltFinal = dataUltFinal;
	}
	
}