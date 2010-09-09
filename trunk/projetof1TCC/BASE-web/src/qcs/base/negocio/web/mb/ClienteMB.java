package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.mb.LovDispositivoMB;
import qcs.base.negocio.Cliente;
import qcs.base.negocio.web.dataprov.ClienteDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.ClienteView;

public class ClienteMB extends BaseMB {
	protected static Log log = LogFactory.getLog(ClienteMB.class);
	private static final long serialVersionUID = 1L;

	private ClienteDataProvider clienteDataProvider;
	private Cliente cliente;
	private ClienteView view;
	private Map<String, Object> atributosFiltros;
	private LovDispositivoMB lovDispositivoMB;	

	//FILTROS DA TELA
	private String nome;
	
	private Long statusCliente;
	private qcs.base.negocio.web.dataprov.CatracaDataProvider catracaDataProvider;
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(Long statusCliente) {
		this.statusCliente = statusCliente;
	}
	
	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();

		atributosFiltros.remove("nome");
		atributosFiltros.put("nome",nome);
		atributosFiltros.remove("idStatusCliente");
		atributosFiltros.put("idStatusCliente", statusCliente);

		return atributosFiltros;
	}
	
	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public ClienteDataProvider getClienteDataProvider() {
		if(clienteDataProvider == null){
			clienteDataProvider = (ClienteDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "clienteDataProvider");
		}
		return clienteDataProvider;
	}

	public void setClienteDataProvider(ClienteDataProvider clienteDataProvider) {
		this.clienteDataProvider = clienteDataProvider;
	}


	public ClienteView getView() {
		return view;
	}

	public void setView(ClienteView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Cliente: "+cliente.getIdCliente());

			cliente = getClienteDataProvider().incluir(cliente);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nome = "";
		this.statusCliente = null;
		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.cliente = new Cliente();
		}else{
			this.cliente = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando Cliente: "+cliente.getIdCliente());
			getClienteDataProvider().alterar(cliente);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Cliente: "+cliente.getIdCliente());
			getClienteDataProvider().excluir(cliente);
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
			return "Id:" + cliente.getIdCliente()+ " Nome Cliente:" + cliente.getNome();
		}else if(isEditarState()){
			return "Id:" + view.getIdCliente()+ " Nome Cliente:" + view.getNome();	
		}else if(isPesquisarState()){
			return "Id:" + cliente.getIdCliente()+ " Nome Cliente:" + cliente.getNome();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Cliente: "+view.getIdCliente());
			cliente = getClienteDataProvider().consultar(view.getIdCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Cliente: "+view.getIdCliente());
			cliente = getClienteDataProvider().consultar(view.getIdCliente());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Cliente: "+view.getIdCliente());
			cliente = getClienteDataProvider().consultar(view.getIdCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public qcs.base.negocio.web.dataprov.CatracaDataProvider getCatracaDataProvider() {
		return catracaDataProvider;
	}

	public void setCatracaDataProvider(
			qcs.base.negocio.web.dataprov.CatracaDataProvider catracaDataProvider) {
		this.catracaDataProvider = catracaDataProvider;
	}
	
	public void atualizarSelecao(){
		try{			
			cliente.setDispositivo(
					getLovDispositivoMB().getDispositivoById());
						
			log.debug("Novo Dispositivo: "+cliente.getNome());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{
			cliente.setDispositivo(null);
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public LovDispositivoMB getLovDispositivoMB() {
		return lovDispositivoMB;
	}

	public void setLovDispositivoMB(LovDispositivoMB lovDispositivoMB) {
		this.lovDispositivoMB = lovDispositivoMB;
	}


	
	
}