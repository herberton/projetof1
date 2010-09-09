package qcs.base.lov.web.mb;

import java.util.Map;

import qcs.base.lov.web.dataprov.LovDispositivoDataProvider;
import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.web.dataprov.DispositivoDataProvider;
import qcs.base.web.message.GeneralMessagesUtil;
import qcs.datamodel.BaseMB;

public class LovDispositivoMB extends BaseMB{
	private static final long serialVersionUID = 1L;

	private DispositivoDataProvider dataProvider;
	private Map<String, Object> atributosFiltros;
	private Long idDispositivoSelecionado;
	private String exibir;
	private Dispositivo dispotivo;

	//Filtros da tela de pesquisa
	private String nomeDispositivo;

	private LovDispositivoDataProvider lovDispositivoDataProvider;

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
		
//		if(atributosFiltros == null)atributosFiltros = new HashMap<String, Object>();
//		atributosFiltros.remove("nomeDispositivo");
//		atributosFiltros.put("nomeDispositivo", (nomeDispositivo == null ? "" : nomeDispositivo));

		return atributosFiltros;
	}

	public void setAtributosFiltros(Map<String, Object> atributosFiltros) {
		this.atributosFiltros = atributosFiltros;
	}

	public DispositivoDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DispositivoDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Long getIdDispositivoSelecionado() {
		return idDispositivoSelecionado;
	}

	public void setIdDispositivoSelecionado(Long idDispositivoSelecionado) {
		this.idDispositivoSelecionado = idDispositivoSelecionado;
	}

	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public Dispositivo getDispositivo() {
		return dispotivo;
	}

	public void setDispositivo(Dispositivo dispotivo) {
		this.dispotivo = dispotivo;
	}

	public Dispositivo getDispotivo() {
		return dispotivo;
	}

	public void setDispotivo(Dispositivo dispotivo) {
		this.dispotivo = dispotivo;
	}

	public String getNomeDispositivo() {
		return nomeDispositivo;
	}

	public void setNomeDispositivo(String nomeDispositivo) {
		this.nomeDispositivo = nomeDispositivo;
	}

	public Dispositivo getDispositivoById(){
		try{
			return getDataProvider().consultar(getIdDispositivoSelecionado());
		}catch(Exception e){
			mensagem = GeneralMessagesUtil.criarMensagemErroApartirDe(e, "Erro ao carregar dispotivo selecionada: "+idDispositivoSelecionado);
		}
		return null;
	}

	public qcs.base.lov.web.dataprov.LovDispositivoDataProvider getLovDispositivoDataProvider() {
		return lovDispositivoDataProvider;
	}

	public void setLovDispositivoDataProvider(
			qcs.base.lov.web.dataprov.LovDispositivoDataProvider lovDispositivoDataProvider) {
		this.lovDispositivoDataProvider = lovDispositivoDataProvider;
	}
}