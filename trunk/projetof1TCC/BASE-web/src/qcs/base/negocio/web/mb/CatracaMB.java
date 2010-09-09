package qcs.base.negocio.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.lov.web.mb.LovBrinquedoMB;
import qcs.base.negocio.Catraca;
import qcs.base.negocio.web.dataprov.CatracaDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;
import qcs.persistence.rhdefensoria.view.CatracaView;

public class CatracaMB extends BaseMB {
	protected static Log log = LogFactory.getLog(CatracaMB.class);
	private static final long serialVersionUID = 1L;

	private CatracaDataProvider catracaDataProvider;
	private Catraca catraca;
	private CatracaView view;
	private Map<String, Object> atributosFiltros;
	private LovBrinquedoMB lovBrinquedoMB;

	//FILTROS DA TELA
	private String descricao;
	
	private Long idBrinquedo;

	public Long getIdBrinquedo() {
		return idBrinquedo;
	}


	public void setIdBrinquedo(Long idBrinquedo) {
		this.idBrinquedo = idBrinquedo;
	}


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

	public Catraca getCatraca() {
		return catraca;
	}

	public void setCatraca(Catraca catraca) {
		this.catraca = catraca;
	}

	public CatracaDataProvider getCatracaDataProvider() {
		if(catracaDataProvider == null){
			catracaDataProvider = (CatracaDataProvider) 
			getElResolver().getValue(getFacescontext().getELContext(), null, "catracaDataProvider");
		}
		return catracaDataProvider;
	}

	public void setCatracaDataProvider(CatracaDataProvider catracaDataProvider) {
		this.catracaDataProvider = catracaDataProvider;
	}


	public CatracaView getView() {
		return view;
	}

	public void setView(CatracaView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		try{
			log.debug("Incluindo Catraca: "+catraca.getIdCatraca());

			catraca = getCatracaDataProvider().incluir(catraca);
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
			this.catraca = new Catraca();
		}else{
			this.catraca = null;
		}
	}

	@Override
	public void editar() {
		try{
			log.debug("Editando Catraca: "+catraca.getIdCatraca());
			getCatracaDataProvider().alterar(catraca);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe(getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Catraca: "+catraca.getIdCatraca());
			getCatracaDataProvider().excluir(catraca);
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
			return "Id:" + catraca.getIdCatraca()+ " Descrição Catraca:" + catraca.getDescricao();
		}else if(isEditarState()){
			return "Id:" + view.getIdCatraca()+ " Descrição Catraca:" + view.getDescricao();	
		}else if(isPesquisarState()){
			return "Id:" + catraca.getIdCatraca()+ " Descrição Catraca:" + catraca.getDescricao();
		}else return null; 
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Catraca: "+view.getIdCatraca());
			catraca = getCatracaDataProvider().consultar(view.getIdCatraca());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			log.debug("Editando Catraca: "+view.getIdCatraca());
			catraca = getCatracaDataProvider().consultar(view.getIdCatraca());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Carregando Excluindo Catraca: "+view.getIdCatraca());
			catraca = getCatracaDataProvider().consultar(view.getIdCatraca());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	public void atualizarSelecao(){
		try{			
			catraca.setBrinquedo(
					getLovBrinquedoMB().getBrinquedoById());
						
			log.debug("Novo Pessoa: "+catraca.getDescricao());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{
			catraca.setBrinquedo(null);
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, getTextoDocumento());
		}
	}

	public LovBrinquedoMB getLovBrinquedoMB() {
		return lovBrinquedoMB;
	}


	public void setLovBrinquedoMB(LovBrinquedoMB lovBrinquedoMB) {
		this.lovBrinquedoMB = lovBrinquedoMB;
	}

	
	
}