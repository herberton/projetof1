package qcs.base.lov.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.web.dataprov.BairroDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LovBairroMB extends BaseMB{
	protected static Log log = LogFactory.getLog(LovBairroMB.class);
	private static final long serialVersionUID = 1L;

	private BairroDataProvider BairroDataProvider;
	private Map<String, Object> atributosFiltros;
	private String exibir;
	private Long idBairroSelecionado;

	private String nome;
	private String nomeCidade;
	
	public Bairro getBairroById(){
		try{
			return getBairroDataProvider().consultar(getIdBairroSelecionado());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Bairro: ");
		}
		return null;
	}
	
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
		atributosFiltros.remove("nome");
		atributosFiltros.remove("idValtabauxUf");
		
		atributosFiltros.put("nome", nome== null ? "" : nome);
		atributosFiltros.put("nomeCidade", nomeCidade);
		
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public String getExibir() {
		if(exibir==null)
			exibir="false";
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Long getIdBairroSelecionado() {
		return idBairroSelecionado;
	}

	public void setIdBairroSelecionado(Long idBairroSelecionado) {
		this.idBairroSelecionado = idBairroSelecionado;
	}

	public BairroDataProvider getBairroDataProvider() {
		return BairroDataProvider;
	}

	public void setBairroDataProvider(BairroDataProvider BairroDataProvider) {
		this.BairroDataProvider = BairroDataProvider;
	}

	public Bairro getBairro() throws Exception{
		return getBairroDataProvider().consultar(getIdBairroSelecionado());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	
}