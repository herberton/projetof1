package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.mb.LovAssociaDispositivoMB;
import qcs.base.negocio.Cliente;
import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.web.dataprov.ClienteDataProvider;
import qcs.base.negocio.web.dataprov.DispositivoDataProvider;
import qcs.base.negocio.web.dataprov.HistoricoClienteDataProvider;
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
	private LovAssociaDispositivoMB lovAssociaDispositivoMB;	
	private DispositivoDataProvider dispositivoDataProvider;
	private Dispositivo dispositivo;
	private HistoricoClienteDataProvider historicoClienteDataProvider;

	//VARIAVEIS
	private Integer codDispositivo;
	private boolean validaBoolean= false;


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
	protected void clear() {
		this.nome = "";
		this.RG = "";
		this.CPF = "";
		this.statusCliente = null;
		this.dispositivo = null;

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


	public ClienteDataProvider getDataProvider() {
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

	public Integer getCodDispositivo() {
		return codDispositivo;
	}

	public void setCodDispositivo(Integer codDispositivo) {
		this.codDispositivo = codDispositivo;
	}

	public Boolean getValidaBoolean() {
		System.out.println("GET DO ATRIBUTO FOI CHAMADO : " + validaBoolean);
		return validaBoolean;
	}

	public void setValidaBoolean(Boolean validaBoolean) {
		this.validaBoolean = validaBoolean;
	}

	public LovAssociaDispositivoMB getLovAssociaDispositivoMB() {
		return lovAssociaDispositivoMB;
	}

	public void setLovAssociaDispositivoMB(
			LovAssociaDispositivoMB lovAssociaDispositivoMB) {
		this.lovAssociaDispositivoMB = lovAssociaDispositivoMB;
	}
	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Cliente: "+cliente.getIdCliente());
			cliente = getDataProvider().incluir(cliente);
			
			if(cliente.getDispositivo() != null){
				cliente = dataProvider.retornaCliente(cliente.getCpf());
				dispositivo.setCliente(cliente);
				dispositivoDataProvider.alterar(dispositivo);
				historicoClienteDataProvider.insereHistoricoClienteEntradaParque(cliente, dispositivo, dispositivo.getStatusDispositivo());				
			}

			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}	

	public void atualizarSelecao(){
		try{			

			if(!isAdicionarState()){
				
				if (view != null)
					cliente = getDataProvider().consultar(view.getIdCliente());
				
				dispositivo = lovAssociaDispositivoMB.getDispositivoSelecionado();
				cliente.setDispositivo(dispositivo);
				cliente.setQtdVisitas(cliente.getQtdVisitas() + 1);
				editar();
				dispositivo.setCliente(cliente);
				dispositivoDataProvider.alterar(dispositivo);
				historicoClienteDataProvider.insereHistoricoClienteEntradaParque(cliente, dispositivo, dispositivo.getStatusDispositivo());
			}else{
				dispositivo = lovAssociaDispositivoMB.getDispositivoSelecionado();	
				cliente.setDispositivo(dispositivo);
			}


		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{

			if(!isAdicionarState()){	
				if (view != null)
					cliente = getDataProvider().consultar(view.getIdCliente());
				
				dispositivo = cliente.getDispositivo();
				dispositivo.setCliente(null);
				dispositivoDataProvider.alterar(dispositivo);			
				cliente.setDispositivo(null);
				editar();
				historicoClienteDataProvider.insereHistoricoClienteSaidaParque(cliente);
			}else{
				cliente.setDispositivo(null);
				dispositivo = null;
			}


		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public DispositivoDataProvider getDispositivoDataProvider() {
		return dispositivoDataProvider;
	}

	public void setDispositivoDataProvider(
			DispositivoDataProvider dispositivoDataProvider) {
		this.dispositivoDataProvider = dispositivoDataProvider;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public HistoricoClienteDataProvider getHistoricoClienteDataProvider() {
		return historicoClienteDataProvider;
	}

	public void setHistoricoClienteDataProvider(
			HistoricoClienteDataProvider historicoClienteDataProvider) {
		this.historicoClienteDataProvider = historicoClienteDataProvider;
	}	
	
	public String carregarHistoricoCliente(){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("clienteView", view);
		
		return "historico_cliente";
	}	

	

}