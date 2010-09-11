package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.Brinquedo;
import qcs.base.negocio.web.dataprov.BrinquedoDataProvider;
import qcs.base.negocio.web.dataprov.StatusBrinquedoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.BrinquedoView;

public class BrinquedoMB extends BaseMB {
	protected static Log log = LogFactory.getLog(BrinquedoMB.class);
	private static final long serialVersionUID = 1L;

	private BrinquedoDataProvider brinquedoDataProvider;
	private StatusBrinquedoDataProvider statusBrinquedoDataProvider;
	private Brinquedo brinquedo;
	private BrinquedoView view;
	private Map<String, Object> atributosFiltros;

	private Long idStatusBrinquedoTransient;

	//FILTROS DA TELA
	private String nome;
	private Long statusBrinquedo;


	public Long getIdStatusBrinquedoTransient() {
		if(!isAdicionarState())
			idStatusBrinquedoTransient = brinquedo.getStatusBrinquedo().getIdStatusBrinquedo();
		return idStatusBrinquedoTransient;
	}

	public void setIdStatusBrinquedoTransient(Long idStatusBrinquedoTransient) {
		this.idStatusBrinquedoTransient = idStatusBrinquedoTransient;
	}

	public Brinquedo getBrinquedo() {
		return brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getStatusBrinquedo() {
		return statusBrinquedo;
	}

	public void setStatusBrinquedo(Long statusBrinquedo) {
		this.statusBrinquedo = statusBrinquedo;
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();

		atributosFiltros.remove("nome");
		atributosFiltros.put("nome",nome);
		atributosFiltros.remove("idStatusBrinquedo");
		atributosFiltros.put("idStatusBrinquedo", statusBrinquedo);

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public BrinquedoDataProvider getBrinquedoDataProvider() {
		if(brinquedoDataProvider == null){
			brinquedoDataProvider = (BrinquedoDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "brinquedoDataProvider");
		}
		return brinquedoDataProvider;
	}

	public void setBrinquedoDataProvider(BrinquedoDataProvider brinquedoDataProvider) {
		this.brinquedoDataProvider = brinquedoDataProvider;
	}


	public BrinquedoView getView() {
		return view;
	}

	public void setView(BrinquedoView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Brinquedo: "+brinquedo.getIdBrinquedo());
			brinquedo.setStatusBrinquedo(getStatusBrinquedoDataProvider().consultar(idStatusBrinquedoTransient));
			brinquedo = getBrinquedoDataProvider().incluir(brinquedo);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nome = "";
		this.statusBrinquedo = null;

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.brinquedo = new Brinquedo();
		}else{
			this.brinquedo = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando Brinquedo: "+brinquedo.getIdBrinquedo());
			brinquedo.setStatusBrinquedo(getStatusBrinquedoDataProvider().consultar(idStatusBrinquedoTransient));
			getBrinquedoDataProvider().alterar(brinquedo);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Brinquedo: "+brinquedo.getIdBrinquedo());
			getBrinquedoDataProvider().excluir(brinquedo);
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
			return "Id:" + brinquedo.getIdBrinquedo()+ " Nome Brinquedo:" + brinquedo.getNome();
		}else if(isEditarState()){
			return "Id:" + view.getIdBrinquedo()+ " Nome Brinquedo:" + view.getNome();	
		}else if(isPesquisarState()){
			return "Id:" + brinquedo.getIdBrinquedo()+ " Nome Brinquedo:" + brinquedo.getNome();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Brinquedo: "+view.getIdBrinquedo());
			brinquedo = getBrinquedoDataProvider().consultar(view.getIdBrinquedo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Brinquedo: "+view.getIdBrinquedo());
			brinquedo = getBrinquedoDataProvider().consultar(view.getIdBrinquedo());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Brinquedo: "+view.getIdBrinquedo());
			brinquedo = getBrinquedoDataProvider().consultar(view.getIdBrinquedo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public StatusBrinquedoDataProvider getStatusBrinquedoDataProvider() {
		return statusBrinquedoDataProvider;
	}

	public void setStatusBrinquedoDataProvider(
			StatusBrinquedoDataProvider statusBrinquedoDataProvider) {
		this.statusBrinquedoDataProvider = statusBrinquedoDataProvider;
	}


}