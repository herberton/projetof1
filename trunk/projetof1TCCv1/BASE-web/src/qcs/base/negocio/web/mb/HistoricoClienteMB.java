package qcs.base.negocio.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.HistoricoCliente;
import qcs.base.negocio.web.dataprov.HistoricoClienteDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.HistoricoClienteView;

public class HistoricoClienteMB extends BaseMB {
	protected static Log log = LogFactory.getLog(HistoricoClienteMB.class);
	private static final long serialVersionUID = 1L;

	private HistoricoClienteDataProvider historicoClienteDataProvider;
	private HistoricoCliente historicoCliente;
	private HistoricoClienteView view;
	private Map<String, Object> atributosFiltros;

	
	// CAMPOS FILTROS DA TELA
	private String nome;
	private Date dataEntrada;
	private Date dataSaida;
	

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public HistoricoCliente getHistoricoCliente() {
		return historicoCliente;
	}

	public void setHistoricoCliente(HistoricoCliente historicoCliente) {
		this.historicoCliente = historicoCliente;
	}



	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();

		//atributosFiltros.remove("nome");
		//atributosFiltros.put("nome",nome);


		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public HistoricoClienteDataProvider getHistoricoClienteDataProvider() {
		if(historicoClienteDataProvider == null){
			historicoClienteDataProvider = (HistoricoClienteDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "historicoClienteDataProvider");
		}
		return historicoClienteDataProvider;
	}

	public void setHistoricoClienteDataProvider(HistoricoClienteDataProvider historicoClienteDataProvider) {
		this.historicoClienteDataProvider = historicoClienteDataProvider;
	}


	public HistoricoClienteView getView() {
		return view;
	}

	public void setView(HistoricoClienteView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo HistoricoCliente: "+historicoCliente.getIdHistoricoCliente());
			historicoCliente = getHistoricoClienteDataProvider().incluir(historicoCliente);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		//this.nome = "";

		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.historicoCliente = new HistoricoCliente();
		}else{
			this.historicoCliente = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando HistoricoCliente: "+historicoCliente.getIdHistoricoCliente());
			getHistoricoClienteDataProvider().alterar(historicoCliente);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo HistoricoCliente: "+historicoCliente.getIdHistoricoCliente());
			getHistoricoClienteDataProvider().excluir(historicoCliente);
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
			return "Id:" + historicoCliente.getIdHistoricoCliente()+ " Nome HistoricoCliente:" + historicoCliente.getIdHistoricoCliente();
		}else if(isEditarState()){
			return "Id:" + view.getId_historico_cliente()+ "HistoricoCliente:" + view.getId_historico_cliente();	
		}else if(isPesquisarState()){
			return "Id:" + historicoCliente.getIdHistoricoCliente()+ "HistoricoCliente:" + historicoCliente.getIdHistoricoCliente();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando HistoricoCliente: "+view.getId_historico_cliente());
			historicoCliente = getHistoricoClienteDataProvider().consultar(view.getId_historico_cliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando HistoricoCliente: "+view.getId_historico_cliente());
			historicoCliente = getHistoricoClienteDataProvider().consultar(view.getId_historico_cliente());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo HistoricoCliente: "+view.getId_historico_cliente());
			historicoCliente = getHistoricoClienteDataProvider().consultar(view.getId_historico_cliente());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}



}