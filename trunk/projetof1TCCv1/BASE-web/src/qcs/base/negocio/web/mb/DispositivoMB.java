package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.web.dataprov.DispositivoDataProvider;
import qcs.base.negocio.web.dataprov.StatusDispositivoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.DispositivoView;

public class DispositivoMB extends BaseMB{

	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(DispositivoMB.class);
	
	private Dispositivo dispositivo;
	private DispositivoDataProvider dataProvider;
	private StatusDispositivoDataProvider statusDispositivoDataProvider;
	private DispositivoView view;
	private Long IdStatusDispositivoTransient;
	
	//FILTROS
	private String ip;
	private String utilizados;
	private Map<String, Object> atributosFiltros;
	private Long IdStatusDispositivo;
	
	
	public DispositivoMB() { super(); }
	
	
	public StatusDispositivoDataProvider getStatusDispositivoDataProvider() {
		return statusDispositivoDataProvider;
	}

	public void setStatusDispositivoDataProvider(
			StatusDispositivoDataProvider statusDispositivoDataProvider) {
		this.statusDispositivoDataProvider = statusDispositivoDataProvider;
	}

	public Long getIdStatusDispositivo() {
		return IdStatusDispositivo;
	}

	public void setIdStatusDispositivo(Long idStatusDispositivo) {
		IdStatusDispositivo = idStatusDispositivo;
	}




	public Long getIdStatusDispositivoTransient() {
		if(!isAdicionarState())
			IdStatusDispositivoTransient = dispositivo.getStatusDispositivo().getIdStatusDispositivo();
		return IdStatusDispositivoTransient;
	}

	public void setIdStatusDispositivoTransient(Long IdStatusDispositivoTransient) {
		this.IdStatusDispositivoTransient = IdStatusDispositivoTransient;
	}
	
	public String getUtilizados() {
		return utilizados;
	}
	public Dispositivo getDispositivo() {
		return dispositivo;
	}
	public DispositivoDataProvider getDataProvider() {
		if(dataProvider == null){
			dataProvider = (DispositivoDataProvider)
				getElResolver().getValue(getFacescontext().getELContext(), null, "dispositivoDataProvider");
		}
		return dataProvider;
	}
	public DispositivoView getView() {
		return view;
	}
	public String getIp() {
		return ip;
	}

	public Map<String, Object> getAtributosFiltros() {
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		
		//IP...
		atributosFiltros.remove("ip");
		atributosFiltros.put("ip", getIp());
		
		//STATUS DISPOSITIVO...
		atributosFiltros.remove("idStatusDispositivo");
		atributosFiltros.put("idStatusDispositivo", IdStatusDispositivo);
		
		
		atributosFiltros.remove("utilizados");
		atributosFiltros.put("utilizados", utilizados);
		
		return atributosFiltros;
	}
	
	
	//SET...


	public void setUtilizados(String utilizados) {
		this.utilizados = utilizados;
	}
	
	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	public void setDataProvider(DispositivoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	public void setView(DispositivoView view) {
		this.view = view;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}
	
	
	//MÉTODOS...
	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Dispositivo: " + getDispositivo().getIdDispositivo());
			
			dispositivo.setStatusDispositivo(statusDispositivoDataProvider.consultar(IdStatusDispositivoTransient));
			dispositivo = getDataProvider().incluir(dispositivo);
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		
		setIp(null);
		setIdStatusDispositivo(null);
		
		if(getCurrentState() == null| getCurrentState().equals(PESQUISAR_STATE)){
			setDispositivo(new Dispositivo());
		}else{
			setDispositivo(null);
		}
		
	}

	@Override
	public void editar() {
		
		try{
			log.debug("Editando Dispositivo: " + getDispositivo().getIdDispositivo());
			
			dispositivo.setStatusDispositivo(statusDispositivoDataProvider.consultar(IdStatusDispositivoTransient));
			getDataProvider().alterar(dispositivo);
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
		
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Dispositivo: " + getDispositivo().getIdDispositivo());
			
			getDataProvider().excluir(dispositivo);
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
			return "Id:" + getDispositivo().getIdDispositivo() + " IP do Dispositivo:" + getDispositivo().getIp();
		}else{
			if(isEditarState()){
				return "Id:" + getView().getIdDispositivo() + " IP do Dispositivo:" + getView().getIp();
			}else{
				return null;
			}
		}
	}
	
	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Dispositivo: " + getView().getIdDispositivo());
			
			dispositivo = getDataProvider().consultar(view.getIdDispositivo());
			this.pesquisar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Dispositivo: " + getView().getIdDispositivo());
			
			dispositivo = getDataProvider().consultar(view.getIdDispositivo());
			prepareEditar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Dispositivo: " + getView().getIdDispositivo());
			
			dispositivo = getDataProvider().consultar(view.getIdDispositivo());
			pesquisar();
			
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

}
