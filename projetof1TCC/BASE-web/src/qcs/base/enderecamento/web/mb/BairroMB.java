package qcs.base.enderecamento.web.mb;

import java.util.HashMap;
import java.util.Map;

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.persistence.view.BairroView;
import qcs.base.enderecamento.web.dataprov.BairroDataProvider;
import qcs.base.lov.web.mb.LovCidadeMB;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class BairroMB extends BaseMB {
	private static final long serialVersionUID = 1L;

	private BairroDataProvider bairroDataProvider;
	private Bairro bairro;
	private Map<String, Object> atributosFiltros;
	private BairroView view;
	
	private LovCidadeMB lovCidadeMB;

	//Filtros da tela
	private String nome;
	
	public void atualizarSelecao(){
		try{
			bairro.setCidade(
					getLovCidadeMB().getCidadeById());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "bairro: "+getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{
			bairro.setCidade(null);
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "bairro: "+getTextoDocumento());
		}
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("nome");
		
		atributosFiltros.put("nome", (nome == null ? "" : nome));
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public BairroDataProvider getBairroDataProvider() {
		return bairroDataProvider;
	}

	public void setBairroDataProvider(BairroDataProvider bairroDataProvider) {
		this.bairroDataProvider = bairroDataProvider;
	}

	@Override
	public void adicionar() {
		try{
			bairro = getBairroDataProvider().incluir(bairro);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Bairro: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nome = "";
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.bairro = new Bairro();
		}else{
			this.bairro = null;
		}
	}

	@Override
	public void editar() {
		try{
			getBairroDataProvider().alterar(bairro);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("Bairro: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {

		try{
			getBairroDataProvider().excluir(bairro);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("Bairro: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}

	@Override
	public void salvar() {
	}
	
	public void carregarVisualizacao(){
		try{
			bairro = getBairroDataProvider().consultar(view.getIdBairro());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}
	
	public void carregarEdicao(){
		try{
			bairro = getBairroDataProvider().consultar(view.getIdBairro());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}
	
	public void carregarExclusao(){
		try{
			bairro = getBairroDataProvider().consultar(view.getIdBairro());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: "+getTextoDocumento());
		}
	}	

	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return bairro.getNome().toUpperCase()+"";
	}
	
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public BairroView getView() {
		return view;
	}

	public void setView(BairroView view) {
		this.view = view;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public LovCidadeMB getLovCidadeMB() {
		return lovCidadeMB;
	}
	public void setLovCidadeMB(LovCidadeMB lovCidadeMB) {
		this.lovCidadeMB = lovCidadeMB;
	}
}