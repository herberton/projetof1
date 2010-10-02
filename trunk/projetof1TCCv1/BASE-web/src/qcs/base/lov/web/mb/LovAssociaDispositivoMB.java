package qcs.base.lov.web.mb;

import qcs.datamodel.BaseMB;

public class LovAssociaDispositivoMB extends BaseMB{
	private static final long serialVersionUID = 1L;

	private Long idDispositivoSelecionado;
	private String exibir;
	private String codDispositivo;
	private boolean validaBoolean= false;

	
	public void validaDispositivo(){		
		
		
		System.out.println("METODO CHAMADOOOOOOO");
		validaBoolean = !validaBoolean;
		System.out.println("validaBoolean=" + validaBoolean);
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

}