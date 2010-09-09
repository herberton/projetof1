package qcs.base.lov.web.mb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.enderecamento.Cidade;
import qcs.base.enderecamento.web.dataprov.CidadeDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LovCidadeMB extends BaseMB{
	protected static Log log = LogFactory.getLog(LovCidadeMB.class);
	private static final long serialVersionUID = 1L;

	private CidadeDataProvider cidadeDataProvider;
	private Map<String, Object> atributosFiltros;
	private String exibir;
	private Long idCidadeSelecionado;

	private String nome;
	private Long idValTabAuxUf;
	
	public Cidade getCidadeById(){
		try{
			return getCidadeDataProvider().consultar(getIdCidadeSelecionado());
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
		atributosFiltros.put("idValTabAuxUf", idValTabAuxUf);
		
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Long getIdCidadeSelecionado() {
		return idCidadeSelecionado;
	}

	public void setIdCidadeSelecionado(Long idCidadeSelecionado) {
		this.idCidadeSelecionado = idCidadeSelecionado;
	}

	public CidadeDataProvider getCidadeDataProvider() {
		return cidadeDataProvider;
	}

	public void setCidadeDataProvider(CidadeDataProvider cidadeDataProvider) {
		this.cidadeDataProvider = cidadeDataProvider;
	}

	public Cidade getCidade() throws Exception{
		return getCidadeDataProvider().consultar(getIdCidadeSelecionado());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdValTabAuxUf() {
		return idValTabAuxUf;
	}

	public void setIdValTabAuxUf(Long idValTabAuxUf) {
		if(idValTabAuxUf.longValue()==Long.MIN_VALUE)this.idValTabAuxUf = null;
		else this.idValTabAuxUf = idValTabAuxUf;
	}
	
}