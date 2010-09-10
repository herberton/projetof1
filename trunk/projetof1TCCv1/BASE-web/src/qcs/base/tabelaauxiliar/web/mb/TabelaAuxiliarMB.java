package qcs.base.tabelaauxiliar.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.base.tabelaauxiliar.web.dataprov.TabelaAuxiliarDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class TabelaAuxiliarMB extends BaseMB {
	protected static Log log = LogFactory.getLog(TabelaAuxiliarMB.class);
	private static final long serialVersionUID = 1L;

	private TabelaAuxiliarDataProvider tabelaAuxiliarDataProvider;
	private TabelaAuxiliar tabelaAuxiliar;
	private TabelaAuxiliarView view;
	private Map<String, Object> atributosFiltros;


	//Filtros da tela
	private Long idTabelaAuxiliar;
	private String nomeTabelaAuxiliar;
	private String descTabelaAuxiliar;
	
	
	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		
		atributosFiltros.remove("idTabelaAuxiliar");
		atributosFiltros.remove("nomeTabelaAuxiliar");
		atributosFiltros.remove("descTabelaAuxiliar");
		
		atributosFiltros.put("idTabelaAuxiliar", idTabelaAuxiliar);
		atributosFiltros.put("nomeTabelaAuxiliar", nomeTabelaAuxiliar);
		atributosFiltros.put("descTabelaAuxiliar", descTabelaAuxiliar);

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public TabelaAuxiliar getTabelaAuxiliar() {
		return tabelaAuxiliar;
	}

	public void setTabelaAuxiliar(TabelaAuxiliar tabelaAuxiliar) {
		this.tabelaAuxiliar = tabelaAuxiliar;
	}

	public TabelaAuxiliarDataProvider getTabelaAuxiliarDataProvider() {
		if(tabelaAuxiliarDataProvider == null){
			tabelaAuxiliarDataProvider = (TabelaAuxiliarDataProvider) getElResolver().getValue(getFacescontext().getELContext(), null, "tabelaAuxiliarDataProvider");
		}
		return tabelaAuxiliarDataProvider;
	}

	public void setTabelaAuxiliarDataProvider(TabelaAuxiliarDataProvider tabelaAuxiliarDataProvider) {
		this.tabelaAuxiliarDataProvider = tabelaAuxiliarDataProvider;
	}

	@Override
	public void adicionar() {
		try{	
			
			getTabelaAuxiliarDataProvider().incluir(tabelaAuxiliar);			
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Tabela Auxiliar: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Tabela Auxiliar: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.idTabelaAuxiliar = null;
		this.nomeTabelaAuxiliar = null;
		this.descTabelaAuxiliar = null;
		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.tabelaAuxiliar = new TabelaAuxiliar();
		}else{
			this.tabelaAuxiliar = null;
		}
	}

	@Override
	public void editar() {
		try{
				
			log.debug("editareditar");
			getTabelaAuxiliarDataProvider().alterar(tabelaAuxiliar);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("TabelaAuxiliar: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "TabelaAuxiliar: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {

		try{
			log.debug("Excluindo tabelaAuxiliar: "+tabelaAuxiliar.getDescricao());
			getTabelaAuxiliarDataProvider().excluir(tabelaAuxiliar);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("TabelaAuxiliar: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "TabelaAuxiliar: "+getTextoDocumento());
		}
	}
	
	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando tabelaAuxiliar: "+view.getDescricao()+ "Código:" + view.getIdTabAux());
			tabelaAuxiliar = getTabelaAuxiliarDataProvider().consultar(view.getIdTabAux());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "TabelaAuxiliar: "+getTextoDocumento());
		}
	}
	
	public void carregarEdicao(){	
		try{
			tabelaAuxiliar = getTabelaAuxiliarDataProvider().consultar(view.getIdTabAux());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "TabelaAuxiliar: "+getTextoDocumento());
		}
	}
	
	public void carregarExclusao(){
		try{
			tabelaAuxiliar = getTabelaAuxiliarDataProvider().consultar(view.getIdTabAux());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "TabelaAuxiliar: "+getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
		
	}
	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return tabelaAuxiliar.getDescricao();
	}

	public TabelaAuxiliarView getView() {
		return view;
	}

	public void setView(TabelaAuxiliarView view) {
		log.debug("setView :"+view);
		this.view = view;
	}

	public Long getIdTabelaAuxiliar() {
		return idTabelaAuxiliar;
	}

	public void setIdTabelaAuxiliar(Long idTabelaAuxiliar) {
		
		if (idTabelaAuxiliar == Long.MIN_VALUE)
			this.idTabelaAuxiliar = null;
		else
			this.idTabelaAuxiliar = idTabelaAuxiliar;
	}

	public String getNomeTabelaAuxiliar() {
		return nomeTabelaAuxiliar;
	}

	public void setNomeTabelaAuxiliar(String nomeTabelaAuxiliar) {
		this.nomeTabelaAuxiliar = nomeTabelaAuxiliar;
	}

	public String getDescTabelaAuxiliar() {
		return descTabelaAuxiliar;
	}

	public void setDescTabelaAuxiliar(String descTabelaAuxiliar) {
		this.descTabelaAuxiliar = descTabelaAuxiliar;
	}

	
	public String carregarValorTabelaAuxiliar(){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("tabelaAuxiliarView", view);
		
		return "valor_tabela_auxiliar";
	}

}