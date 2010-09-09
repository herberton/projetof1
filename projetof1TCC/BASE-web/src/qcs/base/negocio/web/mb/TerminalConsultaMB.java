package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.TerminalConsulta;
import qcs.base.negocio.web.dataprov.TerminalConsultaDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.TerminalConsultaView;

public class TerminalConsultaMB extends BaseMB{

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(TerminalConsultaMB.class);
	
	private TerminalConsulta terminalConsulta = null;
	private TerminalConsultaDataProvider terminalConsultaDataProvider = null;
	private TerminalConsultaView view = null;
	
	private String ip = null;
	private String hostName = null;
	private String localizacao = null;
	
	private Map<String, Object> atributosFiltros;
	
	
	//CONSTRUTOR...
	public TerminalConsultaMB() { super(); }
	
	
	//GET...
	public TerminalConsulta getTerminalConsulta() {
		return terminalConsulta;
	}
	public TerminalConsultaDataProvider getTerminalConsultaDataProvider() {
		if(terminalConsultaDataProvider == null){
			terminalConsultaDataProvider = (TerminalConsultaDataProvider)
				getElResolver().getValue(getFacescontext().getELContext(), null, "TerminalConsultaDataProvider");
		}
		return terminalConsultaDataProvider;
	}
	public TerminalConsultaView getView() {
		return view;
	}
	public String getIp() {
		return ip;
	}
	public String getHostName() {
		return hostName;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public Map<String, Object> getAtributosFiltros() {
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		
		//IP...
		atributosFiltros.remove("ip");
		atributosFiltros.put("ip", getIp());
		
		//HOSTNAME...
		atributosFiltros.remove("hostName");
		atributosFiltros.put("hostName", getHostName());
		
		//LOCALIZACAO...
		atributosFiltros.remove("localizacao");
		atributosFiltros.put("localizacao", getLocalizacao());
		
		
		return atributosFiltros;
	}
	
	
	//SET...
	public void setTerminalConsulta(TerminalConsulta terminalConsulta) {
		this.terminalConsulta = terminalConsulta;
	}
	public void setTerminalConsultaDataProvider(TerminalConsultaDataProvider terminalConsultaDataProvider) {
		this.terminalConsultaDataProvider = terminalConsultaDataProvider;
	}
	public void setView(TerminalConsultaView view) {
		this.view = view;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}
	
	
	//MÉTODOS...
	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo TerminalConsulta: " + getTerminalConsulta().getIdTerminalConsulta());
			
			setTerminalConsulta(getTerminalConsultaDataProvider().incluir(getTerminalConsulta()));
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		
		setIp(null);
		setHostName(null);
		setLocalizacao(null);
		setAtributosFiltros(null);
		
		if(getCurrentState() == null| getCurrentState().equals(PESQUISAR_STATE)){
			setTerminalConsulta(new TerminalConsulta());
		}else{
			setTerminalConsulta(null);
		}
		
	}

	@Override
	public void editar() {
		
		try{
			log.debug("Editando TerminalConsulta: " + getTerminalConsulta().getIdTerminalConsulta());
			
			getTerminalConsultaDataProvider().alterar(getTerminalConsulta());
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
		
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo TerminalConsulta: " + getTerminalConsulta().getIdTerminalConsulta());
			
			getTerminalConsultaDataProvider().excluir(getTerminalConsulta());
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
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
		if(isAdicionarState() || isPesquisarState()){
			return "Id:" + getTerminalConsulta().getIdTerminalConsulta() + " IP do TerminalConsulta:" + getTerminalConsulta().getIp();
		}else{
			if(isEditarState()){
				return "Id:" + getView().getIdTerminalConsulta() + " IP do TerminalConsulta:" + getView().getIp();
			}else{
				return null;
			}
		}
	}
	
	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando TerminalConsulta: " + getView().getIdTerminalConsulta());
			
			setTerminalConsulta(getTerminalConsultaDataProvider().consultar(getView().getIdTerminalConsulta()));
			this.pesquisar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando TerminalConsulta: " + getView().getIdTerminalConsulta());
			
			setTerminalConsulta(getTerminalConsultaDataProvider().consultar(getView().getIdTerminalConsulta()));
			prepareEditar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo TerminalConsulta: " + getView().getIdTerminalConsulta());
			
			setTerminalConsulta(getTerminalConsultaDataProvider().consultar(getView().getIdTerminalConsulta()));
			pesquisar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

}
