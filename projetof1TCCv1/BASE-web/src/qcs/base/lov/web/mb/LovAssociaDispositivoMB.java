package qcs.base.lov.web.mb;

import qcs.base.negocio.Dispositivo;
import qcs.base.negocio.web.dataprov.DispositivoDataProvider;
import qcs.datamodel.BaseMB;

public class LovAssociaDispositivoMB extends BaseMB{
	private static final long serialVersionUID = 1L;

	private Dispositivo dispositivoSelecionado = null;
	private String exibir;
	private String codDispositivo = null;
	private boolean validaBoolean= false;
	private DispositivoDataProvider dispositivoDataProvider;

	
	public void validaDispositivo(){		
		
		if(getDispositivoDataProvider().validaDispositivo(codDispositivo) != null){
			dispositivoSelecionado = getDispositivoDataProvider().validaDispositivo(codDispositivo);			
			validaBoolean = true;}
		else
			validaBoolean = false;
	}
	
	public String getCodDispositivo() {
		return codDispositivo;
	}



	public void setCodDispositivo(String codDispositivo) {
		this.codDispositivo = codDispositivo;
	}



	public boolean isValidaBoolean() {
		return validaBoolean;
	}



	public void setValidaBoolean(boolean validaBoolean) {
		this.validaBoolean = validaBoolean;
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



	public Dispositivo getDispositivoSelecionado() {
		return dispositivoSelecionado;
	}



	public void setDispositivoSelecionado(Dispositivo dispositivoSelecionado) {
		this.dispositivoSelecionado = dispositivoSelecionado;
	}



	public String getExibir() {
		return exibir;
	}

	public void setExibir(String exibir) {
		this.exibir = exibir;
	}

	public DispositivoDataProvider getDispositivoDataProvider() {
		return dispositivoDataProvider;
	}

	public void setDispositivoDataProvider(
			DispositivoDataProvider dispositivoDataProvider) {
		this.dispositivoDataProvider = dispositivoDataProvider;
	}

	
	
}