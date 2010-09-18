package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.Cliente;
import qcs.base.negocio.web.dataprov.ClienteDataProvider;
import qcs.base.negocio.web.dataprov.StatusClienteDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.ClienteView;

public class ClienteMB extends BaseMB {
	protected static Log log = LogFactory.getLog(ClienteMB.class);
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private ClienteView view;
	private Map<String, Object> atributosFiltros;	

	//FILTROS DA TELA
	private String nome;
	private String RG;
	private String CPF;

	private Long statusCliente;
	private ClienteDataProvider dataProvider;
	private StatusClienteDataProvider statusClienteDataProvider;

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

		atributosFiltros.remove("CPF");
		atributosFiltros.put("CPF", CPF);
		atributosFiltros.remove("RG");
		atributosFiltros.put("RG", RG);		

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
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

			cliente = getDataProvider().incluir(cliente);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nome = "";
		this.RG = "";
		this.CPF = "";
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
			getDataProvider().alterar(cliente);
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
			getDataProvider().excluir(cliente);
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
			cliente = getDataProvider().consultar(view.getIdCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Cliente: "+view.getIdCliente());
			cliente = getDataProvider().consultar(view.getIdCliente());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Cliente: "+view.getIdCliente());
			cliente = getDataProvider().consultar(view.getIdCliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}



	public qcs.base.negocio.web.dataprov.ClienteDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(
			qcs.base.negocio.web.dataprov.ClienteDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public qcs.base.negocio.web.dataprov.StatusClienteDataProvider getStatusClienteDataProvider() {
		return statusClienteDataProvider;
	}

	public void setStatusClienteDataProvider(
			qcs.base.negocio.web.dataprov.StatusClienteDataProvider statusClienteDataProvider) {
		this.statusClienteDataProvider = statusClienteDataProvider;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}


}