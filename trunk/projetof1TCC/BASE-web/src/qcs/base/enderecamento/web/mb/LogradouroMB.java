package qcs.base.enderecamento.web.mb;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import qcs.base.enderecamento.Logradouro;
import qcs.base.enderecamento.persistence.view.CidadeView;
import qcs.base.enderecamento.persistence.view.LogradouroView;
import qcs.base.enderecamento.web.dataprov.CidadeDataProvider;
import qcs.base.enderecamento.web.dataprov.LogradouroDataProvider;
import qcs.base.lov.web.mb.LovBairroMB;
import qcs.base.tabelaauxiliar.web.dataprov.ValorTabAuxDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LogradouroMB extends BaseMB {
	private static final long serialVersionUID = 1L;

	private LogradouroDataProvider logradouroDataProvider;
	private ValorTabAuxDataProvider valorTabAuxDataProvider;
	private CidadeDataProvider cidadeDataProvider;
	private Logradouro logradouro;
	private Map<String, Object> atributosFiltros;
	private LogradouroView view;
	
	private LovBairroMB lovBairroMB;

	//Filtros da tela
	private String bairro;
	private String nomeLogradouro;
	private String cepRelatorio;
	private CidadeView cidadeView;
	
	public void atualizarSelecao(){
		try{
			logradouro.setBairro(
					getLovBairroMB().getBairroById());
			System.out.println(">> seleção atualizada");
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "logradouro: "+getTextoDocumento());
		}
	}
	public void removerSelecao(){
		try{
			logradouro.setBairro(null);
			setCurrentState(EDITAR_STATE);
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "logradouro: "+getTextoDocumento());
		}
	}

	public Map<String, Object> getAtributosFiltros(){
		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
		atributosFiltros.remove("idCidade");
		atributosFiltros.remove("bairro");
		atributosFiltros.remove("logradouro");
		atributosFiltros.remove("cep");
		atributosFiltros.put("bairro", (bairro == null ? "": bairro));
		atributosFiltros.put("idCidade", getCidadeView().getIdCidade());
		atributosFiltros.put("logradouro", (bairro == null ? "": nomeLogradouro));
		atributosFiltros.put("cep", (cepRelatorio==null ? "":cepRelatorio));
		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public LogradouroDataProvider getLogradouroDataProvider() {
		return logradouroDataProvider;
	}

	public void setLogradouroDataProvider(LogradouroDataProvider logradouroDataProvider) {
		this.logradouroDataProvider = logradouroDataProvider;
	}

	@Override
	public void adicionar() {
		try{
			logradouro.setTipoLog(
					getValorTabAuxDataProvider().consultar(
							logradouro.getIdTipoLog()));
			logradouro = getLogradouroDataProvider().incluir(logradouro);
			
			ClassValidator<Logradouro> validator = new ClassValidator<Logradouro>(Logradouro.class);
			for(InvalidValue value : validator.getInvalidValues(logradouro)){
				System.out.println("name: "+value.getPropertyName()+", message: "+value.getMessage());
			}
			
			this.pesquisar(); 
			mensagem = GeneralMessagesUtil.criarMensagemSucessoInclusaoApartirDe("Logradouro: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}

	@Override
	protected void clear() {
		this.nomeLogradouro = "";
		this.bairro="";
		this.cepRelatorio="";
		
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			System.out.println("novo logradouro");
			this.logradouro = new Logradouro();
		}else{
			this.logradouro = null;
		}
	}

	@Override
	public void editar() {
		try{
			logradouro.setTipoLog(
					getValorTabAuxDataProvider().consultar(
							logradouro.getIdTipoLog()));
			getLogradouroDataProvider().alterar(logradouro);
			setCurrentState(PESQUISAR_STATE);
			mensagem = GeneralMessagesUtil.criarMensagemSucessoAlteracaoApartirDe("Logradouro: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}

	@Override
	public void excluir() {
		try{
			getLogradouroDataProvider().excluir(logradouro);
			this.pesquisar();
			mensagem = GeneralMessagesUtil.criarMensagemSucessoExclusaoApartirDe("Tipo Servidor: "+getTextoDocumento());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}

	public void carregarVisualizacao(){
		try{
			logradouro = getLogradouroDataProvider().consultar(view.getIdLogradouro());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}
	@Override
	public void prepareAdicionar() {
		this.clear();
		try {
			logradouro.setCidade(getCidadeDataProvider().consultar(cidadeView.getIdCidade()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setCurrentState(ADICIONAR_STATE);
	}

	public void carregarEdicao(){
		try{
			logradouro = getLogradouroDataProvider().consultar(view.getIdLogradouro());
			prepareEditar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}

	public void carregarExclusao(){
		try{
			logradouro = getLogradouroDataProvider().consultar(view.getIdLogradouro());
			pesquisar();
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Logradouro: "+getTextoDocumento());
		}
	}	

	@Override
	public void salvar() {
	}
	
	public String carregarCidade(){
		return "cidade";
	}

	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return logradouro.getLogradouro().toUpperCase();
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro cep) {
		this.logradouro = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	public String getCepRelatorio() {
		return cepRelatorio;
	}

	public void setCepRelatorio(String cepRelatorio) {
		this.cepRelatorio = cepRelatorio;
	}
	
	public LogradouroView getView() {
		return view;
	}

	public void setView(LogradouroView view) {
		this.view = view;
	}

	public CidadeView getCidadeView() {
		if(cidadeView == null)
			cidadeView = (CidadeView) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cidadeView");
		return cidadeView;
	}

	public void setCidadeView(CidadeView cidadeView) {
		this.cidadeView = cidadeView;
	}
	public LovBairroMB getLovBairroMB() {
		return lovBairroMB;
	}
	public void setLovBairroMB(LovBairroMB lovBairroMB) {
		this.lovBairroMB = lovBairroMB;
	}
	public ValorTabAuxDataProvider getValorTabAuxDataProvider() {
		return valorTabAuxDataProvider;
	}
	public void setValorTabAuxDataProvider(
			ValorTabAuxDataProvider valorTabAuxDataProvider) {
		this.valorTabAuxDataProvider = valorTabAuxDataProvider;
	}
	public CidadeDataProvider getCidadeDataProvider() {
		return cidadeDataProvider;
	}
	public void setCidadeDataProvider(CidadeDataProvider cidadeDataProvider) {
		this.cidadeDataProvider = cidadeDataProvider;
	}

}