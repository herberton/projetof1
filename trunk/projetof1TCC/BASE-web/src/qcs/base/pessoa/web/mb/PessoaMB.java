package qcs.base.pessoa.web.mb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enums.SexoEnum;
import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.persistence.view.PessoaView;
import qcs.base.pessoa.web.dataprov.PessoaDataProvider;
import qcs.base.web.aplicacao.mb.StatusAplicacao;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class PessoaMB extends BaseMB {
	protected static Log log = LogFactory.getLog(PessoaMB.class);
	private static final long serialVersionUID = 1L;
	public static final String EXECUTAR_ACAO = "executar";
	public SexoEnum sexoEnum;

	private PessoaDataProvider pessoaDataProvider;
	private Pessoa pessoa;
	private PessoaView view;
	private Map<String, Object> atributosFiltros;

	//Filtros
	private String nome;
	private String cpfCnpj;
	private Long idTipoServ;

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("nome");
		atributosFiltros.remove("cpfCnpj");
		atributosFiltros.remove("idTipoServ");

		atributosFiltros.put("nome", (nome == null ? "" : nome));
		atributosFiltros.put("cpfCnpj", (cpfCnpj == null ? "" : cpfCnpj));
		atributosFiltros.put("idTipoServ", idTipoServ);
		return atributosFiltros;
	}
	public boolean isIndividual(){
		return (pessoa.getCpf() != null && pessoa.getCpf()!= "");
	}
	public boolean isCompany(){
		return (pessoa.getCnpj() != null && pessoa.getCnpj()!= "");
	}
	@Override
	public void adicionar() {
		try{
			pessoa.setAtivo("S");
			pessoa.setDataAtivacao(new Date());
			pessoa.setUsuarioAtiva(StatusAplicacao.getUsuarioLogadoNaSessao());
			log.debug("Incluindo Pessoa: "+pessoa.getNomePessoa());
			pessoa = getPessoaDataProvider().incluir(pessoa);
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Pessoa: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Pessoa: "+getTextoDocumento());
		}
	}
	@Override
	protected void clear() {
		this.nome = "";
		this.cpfCnpj = "";
		this.idTipoServ = null;
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.pessoa = new Pessoa();
		}else{
			this.pessoa = null;
		}
	}
	@Override
	public void editar() {
		try{
			log.debug("Editando Pessoa: "+pessoa.getNomePessoa());
			getPessoaDataProvider().alterar(pessoa);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("Pessoa: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Pessoa: "+getTextoDocumento());
		}
	}
	@Override
	public void excluir() {
		try{
			log.debug("Excluindo Pessoa: "+pessoa.getNomePessoa());
			getPessoaDataProvider().excluir(pessoa);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("Pessoa: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Pessoa: "+getTextoDocumento());
		}
	}
	@Override
	public void salvar() {
	}
	public void carregarVisualizacao(){
		try{
			log.debug("Vizualizando Unidade: "+view.getNomePessoa());
			pessoa = getPessoaDataProvider().consultar(view.getIdPessoa());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}
	public void carregarEdicao(){
		try{
			clear();
			pessoa = getPessoaDataProvider().consultar(view.getIdPessoa());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}
	public void carregarExclusao(){
		try{
			log.debug("Excluindo Municipio: "+view.getNomePessoa());
			pessoa = getPessoaDataProvider().consultar(view.getIdPessoa());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Municipio: "+getTextoDocumento());
		}
	}	
	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}
	public String getTextoDocumento(){
		return pessoa.getNomePessoa().toUpperCase();
	}
	public void prepareExecutarAcao() {
		this.clear();
		this.setCurrentState(EXECUTAR_ACAO);
	}
	public boolean isExecutarState() {
		return EXECUTAR_ACAO.equals(this.getCurrentState());
	}
	public PessoaView getView() {
		return view;
	}
	public void setView(PessoaView view) {
		this.view = view;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getIdTipoServ() {
		return idTipoServ;
	}
	public void setIdTipoServ(Long idTipoServ) {
		this.idTipoServ = idTipoServ;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public SexoEnum getSexoEnum() {
		return sexoEnum;
	}
	public void setSexoEnum(SexoEnum sexoEnum) {
		this.sexoEnum = sexoEnum;
	}
	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		System.out.println("setPessoa");
		this.pessoa = pessoa;
	}
	public PessoaDataProvider getPessoaDataProvider() {
		return pessoaDataProvider;
	}
	public void setPessoaDataProvider(PessoaDataProvider pessoaDataProvider) {
		this.pessoaDataProvider = pessoaDataProvider;
	}
}