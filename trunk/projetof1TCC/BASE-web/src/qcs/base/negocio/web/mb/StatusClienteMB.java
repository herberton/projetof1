package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.StatusCliente;
import qcs.base.negocio.web.dataprov.StatusClienteDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.StatusClienteView;

public class StatusClienteMB extends BaseMB {
	protected static Log log = LogFactory.getLog(StatusClienteMB.class);
	private static final long serialVersionUID = 1L;

	private StatusClienteDataProvider statusClienteDataProvider;
	private StatusCliente statusCliente;
	private StatusClienteView view;
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

	public StatusCliente getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(StatusCliente statusCliente) {
		this.statusCliente = statusCliente;
	}

	public StatusClienteDataProvider getStatusClienteDataProvider() {
		if(statusClienteDataProvider == null){
			statusClienteDataProvider = (StatusClienteDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "statusClienteDataProvider");
		}
		return statusClienteDataProvider;
	}

	public void setStatusClienteDataProvider(StatusClienteDataProvider statusClienteDataProvider) {
		this.statusClienteDataProvider = statusClienteDataProvider;
	}


	public StatusClienteView getView() {
		return view;
	}

	public void setView(StatusClienteView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo StatusCliente: "+statusCliente.getIdStatusCliente());

			statusCliente = getStatusClienteDataProvider().incluir(statusCliente);
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
			this.statusCliente = new StatusCliente();
		}else{
			this.statusCliente = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando StatusCliente: "+statusCliente.getIdStatusCliente());
			getStatusClienteDataProvider().alterar(statusCliente);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo StatusCliente: "+statusCliente.getIdStatusCliente());
			getStatusClienteDataProvider().excluir(statusCliente);
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
			return "Id:" + statusCliente.getIdStatusCliente()+ " Descrição StatusCliente:" + statusCliente.getDescricao();
		}else if(isEditarState()){
			return "Id:" + view.getIdStatusCliente()+ " Descrição StatusCliente:" + view.getDescricao();	
		}else if(isPesquisarState()){
			return "Id:" + statusCliente.getIdStatusCliente()+ " Descrição StatusCliente:" + statusCliente.getDescricao();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando StatusCliente: "+view.getIdStatusCliente());
			statusCliente = getStatusClienteDataProvider().consultar(view.getIdStatusCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando StatusCliente: "+view.getIdStatusCliente());
			statusCliente = getStatusClienteDataProvider().consultar(view.getIdStatusCliente());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo StatusCliente: "+view.getIdStatusCliente());
			statusCliente = getStatusClienteDataProvider().consultar(view.getIdStatusCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}


}