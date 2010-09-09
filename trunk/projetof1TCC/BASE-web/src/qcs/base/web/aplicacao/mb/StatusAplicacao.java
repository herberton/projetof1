package qcs.base.web.aplicacao.mb;

import java.io.Serializable;
import java.util.Date;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;

import qcs.base.seguranca.Usuario;

public class StatusAplicacao implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date horaInicioSessao;
	private Usuario usuarioLogado;
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Date getHoraInicioSessao() {
		return horaInicioSessao;
	}

	public void setHoraInicioSessao(Date horaInicioSessao) {
		this.horaInicioSessao = horaInicioSessao;
	}
	
	public static Usuario getUsuarioLogadoNaSessao(){
		return ((StatusAplicacao)getElResolver().getValue(getFacescontext().getELContext(), null, "statusAplicacao")).getUsuarioLogado();
	}	
	
	public static FacesContext getFacescontext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static ELResolver getElResolver(){
		return getFacescontext().getApplication().getELResolver();
	}	
}
