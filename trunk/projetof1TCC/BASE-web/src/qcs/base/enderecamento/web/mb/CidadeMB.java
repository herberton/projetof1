package qcs.base.enderecamento.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.constantes.TabelasAuxiliares;
import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.base.enderecamento.web.dataprov.CidadeDataProvider;
import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabelaauxiliar.web.dataprov.ValorTabAuxDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class CidadeMB extends BaseMB {
	protected static Log log = LogFactory.getLog(CidadeMB.class);
	private static final long serialVersionUID = 1L;
	public static final String EXECUTAR_ACAO = "executar";

	private CidadeDataProvider cidadeDataProvider;
	private ValorTabAuxDataProvider valorTabAuxDataProvider;
	private Cidade cidade;
	private CidadeView view;
	private Map<String, Object> atributosFiltros;

	//Filtros
	private String nome;
	private Long idTabAuxUf;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("nome");
		atributosFiltros.remove("idValTabAuxUf");
		atributosFiltros.put("nome", (nome == null ? "" : nome));
		atributosFiltros.put("idValTabAuxUf", idTabAuxUf );
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;

	}

	public CidadeDataProvider getCidadeDataProvider() {
		return cidadeDataProvider;
	}

	public void setCidadeDataProvider(CidadeDataProvider cidadeDataProvider) {
		this.cidadeDataProvider = cidadeDataProvider;
	}

	@Override
	public void adicionar() {
		try{
			cidade.setUf(
					getValorTabAuxDataProvider().consultar(
						cidade.getIdValTabAux()));
			TabelaAuxiliar tabAux = new TabelaAuxiliar();
			tabAux.setIdTabAux(TabelasAuxiliares.UF);
			cidade.setTabelaAuxiliar(tabAux);
			log.debug("Incluindo Cidade: "+cidade.getNomeCidade());
			cidade = getCidadeDataProvider().incluir(cidade);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Cidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Cidade: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nome = "";
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.cidade = new Cidade();
		}else{
			this.cidade = null;
		}
	}

	@Override
	public void editar() {
		try{
			cidade.setUf(
					getValorTabAuxDataProvider().consultar(
							cidade.getIdValTabAux()));
			log.debug("Editando Cidade: "+cidade.getNomeCidade());
			getCidadeDataProvider().alterar(cidade);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("Cidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Cidade: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Cidade: "+cidade.getNomeCidade());
			getCidadeDataProvider().excluir(cidade);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("Cidade: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Cidade: "+getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
	}

	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Unidade: "+view.getNome());
			cidade = getCidadeDataProvider().consultar(view.getIdCidade());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}

	public void carregarEdicao(){
		try{
			clear();
			cidade = getCidadeDataProvider().consultar(view.getIdCidade());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			log.debug("Excluindo Municipio: "+view.getNome());
			cidade = getCidadeDataProvider().consultar(view.getIdCidade());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}	

	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String carregarLogradouro(){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cidadeView", view);

		return "logradouro";
	}

	public String getTextoDocumento(){
		return cidade.getNomeCidade().toUpperCase();
	}

	public Long getIdTabAuxUf() {
		return idTabAuxUf;
	}

	public void setIdTabAuxUf(Long idTabAuxUf) {
		this.idTabAuxUf = idTabAuxUf;
	}

	public void prepareExecutarAcao() {
		this.clear();
		this.setCurrentState(EXECUTAR_ACAO);
	}

	public boolean isExecutarState() {
		return EXECUTAR_ACAO.equals(this.getCurrentState());
	}

	public CidadeView getView() {
		return view;
	}

	public void setView(CidadeView view) {
		this.view = view;
	}

	public ValorTabAuxDataProvider getValorTabAuxDataProvider() {
		return valorTabAuxDataProvider;
	}

	public void setValorTabAuxDataProvider(
			ValorTabAuxDataProvider valorTabAuxDataProvider) {
		this.valorTabAuxDataProvider = valorTabAuxDataProvider;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}