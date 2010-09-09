package qcs.base.tabelaauxiliar.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import qcs.base.tabaux.ValorTabAux;
import qcs.base.tabaux.persistence.view.TabelaAuxiliarView;
import qcs.base.tabaux.persistence.view.ValorTabAuxView;
import qcs.base.tabelaauxiliar.web.dataprov.TabelaAuxiliarDataProvider;
import qcs.base.tabelaauxiliar.web.dataprov.ValorTabAuxDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class ValorTabAuxMB extends BaseMB {
	private static final long serialVersionUID = 1L;

	private ValorTabAuxDataProvider valorTabAuxDataProvider;
	private TabelaAuxiliarDataProvider tabelaAuxiliarDataProvider;
	private ValorTabAux valorTabAux;
	private ValorTabAuxView view;
	private Map<String, Object> atributosFiltros;
	private TabelaAuxiliarView tabelaAuxiliarView;


	//Filtros da tela
	private Long idTabAux;
	private String nome;
	private String descricao;
	
	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		
		atributosFiltros.remove("idTabAux");
		atributosFiltros.remove("nome");
		atributosFiltros.remove("observacao");
		
		atributosFiltros.put("idTabAux", getTabelaAuxiliarView().getIdTabAux());
		atributosFiltros.put("nome", nome);
		atributosFiltros.put("observacao", descricao);		

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public ValorTabAux getValorTabAux() {
		return valorTabAux;
	}

	public void setValorTabAux(ValorTabAux valorTabAux) {
		this.valorTabAux = valorTabAux;
	}

	public ValorTabAuxDataProvider getValorTabAuxDataProvider() {
		return valorTabAuxDataProvider;
	}

	public void setValorTabAuxDataProvider(ValorTabAuxDataProvider valorTabAuxDataProvider) {
		this.valorTabAuxDataProvider = valorTabAuxDataProvider;
	}

	@Override
	public void adicionar() {
		try{	
			valorTabAux.setTabelaAuxiliar(
				getTabelaAuxiliarDataProvider().consultar(getTabelaAuxiliarView().getIdTabAux()));
			getValorTabAuxDataProvider().incluir(valorTabAux);			
			this.pesquisar(); 
			
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Tabela Auxiliar: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Tabela Auxiliar: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.idTabAux = null;
		this.nome = null;
		this.descricao = null;
		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.valorTabAux = new ValorTabAux();
		}else{
			this.valorTabAux = null;
		}
	}

	@Override
	public void editar() {
		try{
			getValorTabAuxDataProvider().alterar(valorTabAux);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("ValorTabAux: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "ValorTabAux: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {

		try{
			getValorTabAuxDataProvider().excluir(valorTabAux);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("ValorTabAux: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "ValorTabAux: "+getTextoDocumento());
		}
	}
	
	public void carregarVisualizacao(){
		try{
			System.out.println("view.getId()::"+view.getIdValTabAux());
			valorTabAux = getValorTabAuxDataProvider().consultar(view.getIdValTabAux());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "ValorTabAux: "+getTextoDocumento());
		}
	}
	
	public void carregarEdicao(){	
		try{
			valorTabAux = getValorTabAuxDataProvider().consultar(view.getIdValTabAux());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "ValorTabAux: "+getTextoDocumento());
		}
	}
	
	public void carregarExclusao(){
		try{
			valorTabAux = getValorTabAuxDataProvider().consultar(view.getIdValTabAux());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "ValorTabAux: "+getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
		
	}
	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return valorTabAux.getObservacao();
	}

	public ValorTabAuxView getView() {
		return view;
	}

	public void setView(ValorTabAuxView view) {
		this.view = view;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void carregarValorValorTabAux(){
		
	}

	public Long getIdTabAux() {
		return idTabAux;
	}

	public void setIdTabAux(Long idTabAux) {
		this.idTabAux = idTabAux;
	}

	public TabelaAuxiliarView getTabelaAuxiliarView() {
		if(tabelaAuxiliarView == null)
			tabelaAuxiliarView = (TabelaAuxiliarView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tabelaAuxiliarView");
		return tabelaAuxiliarView;
	}

	public TabelaAuxiliarDataProvider getTabelaAuxiliarDataProvider() {
		return tabelaAuxiliarDataProvider;
	}

	public void setTabelaAuxiliarDataProvider(
			TabelaAuxiliarDataProvider tabelaAuxiliarDataProvider) {
		this.tabelaAuxiliarDataProvider = tabelaAuxiliarDataProvider;
	}


}