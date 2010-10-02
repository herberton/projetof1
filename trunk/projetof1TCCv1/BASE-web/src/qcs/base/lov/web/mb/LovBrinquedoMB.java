package qcs.base.lov.web.mb;

import java.util.HashMap;
import java.util.Map;

import qcs.base.lov.web.dataprov.LovBrinquedoDataProvider;
import qcs.base.negocio.Brinquedo;
import qcs.base.negocio.web.dataprov.BrinquedoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LovBrinquedoMB extends BaseMB{
	private static final long serialVersionUID = 1L;

	private BrinquedoDataProvider dataProvider;
	private Map<String, Object> atributosFiltros;
	private Long idBrinquedoSelecionado;
	private String exibir;
	private Brinquedo brinquedo;

	//Filtros da tela de pesquisa
	private String nomeBrinquedo;

	private LovBrinquedoDataProvider lovBrinquedoDataProvider;

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
		atributosFiltros.remove("nomeBrinquedo");
		atributosFiltros.put("nomeBrinquedo", (nomeBrinquedo == null ? "" : nomeBrinquedo));

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public BrinquedoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(BrinquedoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Long getIdBrinquedoSelecionado() {
		return idBrinquedoSelecionado;
	}

	public void setIdBrinquedoSelecionado(Long idBrinquedoSelecionado) {
		this.idBrinquedoSelecionado = idBrinquedoSelecionado;
	}

	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Brinquedo getBrinquedo() {
		return brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}

	public String getNomeBrinquedo() {
		return nomeBrinquedo;
	}

	public void setNomeBrinquedo(String nomeBrinquedo) {
		System.out.println("NOME BRINQUEDO PARAMETRO" + nomeBrinquedo);
		this.nomeBrinquedo = nomeBrinquedo;
	}
	
	public Brinquedo getBrinquedoById(){
		try{
			return getDataProvider().consultar(getIdBrinquedoSelecionado());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Erro ao carregar brinquedo selecionada: "+idBrinquedoSelecionado);
		}
		return null;
	}

	public LovBrinquedoDataProvider getLovBrinquedoDataProvider() {
		return lovBrinquedoDataProvider;
	}

	public void setLovBrinquedoDataProvider(
			LovBrinquedoDataProvider lovBrinquedoDataProvider) {
		this.lovBrinquedoDataProvider = lovBrinquedoDataProvider;
	}
}