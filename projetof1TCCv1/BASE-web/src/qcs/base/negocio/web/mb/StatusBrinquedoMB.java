package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.StatusBrinquedo;
import qcs.base.negocio.web.dataprov.StatusBrinquedoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.StatusBrinquedoView;

public class StatusBrinquedoMB extends BaseMB {
	protected static Log log = LogFactory.getLog(StatusBrinquedoMB.class);
	private static final long serialVersionUID = 1L;

	private StatusBrinquedoDataProvider statusBrinquedoDataProvider;
	private StatusBrinquedo statusBrinquedo;
	private StatusBrinquedoView view;
	private Map<String, Object> atributosFiltros;

	//FILTROS DA TELA
	private String descricao;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();

		atributosFiltros.remove("descricao");
		atributosFiltros.put("descricao",descricao);

		return atributosFiltros;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public StatusBrinquedo getStatusBrinquedo() {
		return statusBrinquedo;
	}

	public void setStatusBrinquedo(StatusBrinquedo statusBrinquedo) {
		this.statusBrinquedo = statusBrinquedo;
	}

	public StatusBrinquedoDataProvider getStatusBrinquedoDataProvider() {
		if(statusBrinquedoDataProvider == null){
			statusBrinquedoDataProvider = (StatusBrinquedoDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "statusBrinquedoDataProvider");
		}
		return statusBrinquedoDataProvider;
	}

	public void setStatusBrinquedoDataProvider(StatusBrinquedoDataProvider statusBrinquedoDataProvider) {
		this.statusBrinquedoDataProvider = statusBrinquedoDataProvider;
	}


	public StatusBrinquedoView getView() {
		return view;
	}

	public void setView(StatusBrinquedoView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo StatusBrinquedo: "+statusBrinquedo.getIdStatusBrinquedo());

			statusBrinquedo = getStatusBrinquedoDataProvider().incluir(statusBrinquedo);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.descricao = "";

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.statusBrinquedo = new StatusBrinquedo();
		}else{
			this.statusBrinquedo = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando StatusBrinquedo: "+statusBrinquedo.getIdStatusBrinquedo());
			getStatusBrinquedoDataProvider().alterar(statusBrinquedo);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo StatusBrinquedo: "+statusBrinquedo.getIdStatusBrinquedo());
			getStatusBrinquedoDataProvider().excluir(statusBrinquedo);
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
			return "Id:" + statusBrinquedo.getIdStatusBrinquedo()+ " Descrição StatusBrinquedo:" + statusBrinquedo.getDescricao();
		}else if(isEditarState()){
			return "Id:" + view.getIdStatusBrinquedo()+ " Descrição StatusBrinquedo:" + view.getDescricao();	
		}else if(isPesquisarState()){
			return "Id:" + statusBrinquedo.getIdStatusBrinquedo()+ " Descrição StatusBrinquedo:" + statusBrinquedo.getDescricao();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando StatusBrinquedo: "+view.getIdStatusBrinquedo());
			statusBrinquedo = getStatusBrinquedoDataProvider().consultar(view.getIdStatusBrinquedo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando StatusBrinquedo: "+view.getIdStatusBrinquedo());
			statusBrinquedo = getStatusBrinquedoDataProvider().consultar(view.getIdStatusBrinquedo());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo StatusBrinquedo: "+view.getIdStatusBrinquedo());
			statusBrinquedo = getStatusBrinquedoDataProvider().consultar(view.getIdStatusBrinquedo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}


}