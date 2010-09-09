package qcs.base.lov.web.mb;

import java.util.HashMap;
import java.util.Map;

import qcs.base.pessoa.Pessoa;
import qcs.base.pessoa.web.dataprov.PessoaDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LovPessoaMB extends BaseMB{
	private static final long serialVersionUID = 1L;

	private PessoaDataProvider dataProvider;
	private Map<String, Object> atributosFiltros;
	private Long idPessoaSelecionado;
	private String exibir;
	private Pessoa pessoa;

	//Filtros da tela de pesquisa
	private Long idTipPes;
	private String nomePessoa;
	private String cpfOuCnpj;

	@Override
	public void adicionar() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir() {
		// TODO Auto-generated method stub

	}

	@Override
	public void salvar() {
		// TODO Auto-generated method stub
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("idTipPes");
		atributosFiltros.remove("nomePessoa");
		atributosFiltros.remove("cpfOuCnpj");
		
		atributosFiltros.put("nomePessoa", (nomePessoa == null ? "" : nomePessoa));
		atributosFiltros.put("cpfOuCnpj", cpfOuCnpj);
		atributosFiltros.put("idTipPes", idTipPes);

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public PessoaDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(PessoaDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Long getIdPessoaSelecionado() {
		return idPessoaSelecionado;
	}

	public void setIdPessoaSelecionado(Long idPessoaSelecionado) {
		this.idPessoaSelecionado = idPessoaSelecionado;
	}

	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getIdTipPes() {
		return idTipPes;
	}

	public void setIdTipPes(Long idTipPes) {
		this.idTipPes = idTipPes;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	
	public Pessoa getPessoaById(){
		try{
			return getDataProvider().consultar(getIdPessoaSelecionado());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Erro ao carregar pessoa selecionada: "+idPessoaSelecionado);
		}
		return null;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
}