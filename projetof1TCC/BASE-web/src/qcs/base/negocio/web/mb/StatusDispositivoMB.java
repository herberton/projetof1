package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.StatusDispositivo;
import qcs.base.negocio.web.dataprov.StatusDispositivoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.StatusDispositivoView;

public class StatusDispositivoMB extends BaseMB {
	protected static Log log = LogFactory.getLog(StatusDispositivoMB.class);
	private static final long serialVersionUID = 1L;

	private StatusDispositivoDataProvider statusDispositivoDataProvider;
	private StatusDispositivo statusDispositivo;
	private StatusDispositivoView view;
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

	public StatusDispositivo getStatusDispositivo() {
		return statusDispositivo;
	}

	public void setStatusDispositivo(StatusDispositivo statusDispositivo) {
		this.statusDispositivo = statusDispositivo;
	}

	public StatusDispositivoDataProvider getStatusDispositivoDataProvider() {
		if(statusDispositivoDataProvider == null){
			statusDispositivoDataProvider = (StatusDispositivoDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "statusDispositivoDataProvider");
		}
		return statusDispositivoDataProvider;
	}

	public void setStatusDispositivoDataProvider(StatusDispositivoDataProvider statusDispositivoDataProvider) {
		this.statusDispositivoDataProvider = statusDispositivoDataProvider;
	}


	public StatusDispositivoView getView() {
		return view;
	}

	public void setView(StatusDispositivoView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo StatusDispositivo: "+statusDispositivo.getIdStatusDispositivo());

			statusDispositivo = getStatusDispositivoDataProvider().incluir(statusDispositivo);
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
			this.statusDispositivo = new StatusDispositivo();
		}else{
			this.statusDispositivo = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando StatusDispositivo: "+statusDispositivo.getIdStatusDispositivo());
			getStatusDispositivoDataProvider().alterar(statusDispositivo);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo StatusDispositivo: "+statusDispositivo.getIdStatusDispositivo());
			getStatusDispositivoDataProvider().excluir(statusDispositivo);
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
			return "Id:" + statusDispositivo.getIdStatusDispositivo()+ " Descrição StatusDispositivo:" + statusDispositivo.getDescricao();
		}else if(isEditarState()){
			return "Id:" + view.getIdStatusDispositivo()+ " Descrição StatusDispositivo:" + view.getDescricao();	
		}else if(isPesquisarState()){
			return "Id:" + statusDispositivo.getIdStatusDispositivo()+ " Descrição StatusDispositivo:" + statusDispositivo.getDescricao();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando StatusDispositivo: "+view.getIdStatusDispositivo());
			statusDispositivo = getStatusDispositivoDataProvider().consultar(view.getIdStatusDispositivo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando StatusDispositivo: "+view.getIdStatusDispositivo());
			statusDispositivo = getStatusDispositivoDataProvider().consultar(view.getIdStatusDispositivo());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo StatusDispositivo: "+view.getIdStatusDispositivo());
			statusDispositivo = getStatusDispositivoDataProvider().consultar(view.getIdStatusDispositivo());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}


}