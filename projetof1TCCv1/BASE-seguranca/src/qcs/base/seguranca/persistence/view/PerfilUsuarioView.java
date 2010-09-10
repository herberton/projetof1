package qcs.base.seguranca.persistence.view;

import java.io.Serializable;
import java.util.Date;

public class PerfilUsuarioView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idPerfilUsuario;
	private boolean ativo;
	private Date dataAtivacao;
	private Date dataDesativacao;
	private Date dataUltAlteracao;
	private String observacao;
	private String usuario;
	private String perfil;
	private String usuarioAtiva;
	private String usuarioDesativa;
	private String usuarioUltAlt;
	
	

	public Long getIdPerfilUsuario() {
		return idPerfilUsuario;
	}
	
	public void setIdPerfilUsuario(Long idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Date getDataAtivacao() {
		return dataAtivacao;
	}
	
	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}
	
	public Date getDataDesativacao() {
		return dataDesativacao;
	}
	
	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}
	
	public Date getDataUltAlteracao() {
		return dataUltAlteracao;
	}
	
	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getUsuarioAtiva() {
		return usuarioAtiva;
	}

	public void setUsuarioAtiva(String usuarioAtiva) {
		this.usuarioAtiva = usuarioAtiva;
	}

	public String getUsuarioDesativa() {
		return usuarioDesativa;
	}

	public void setUsuarioDesativa(String usuarioDesativa) {
		this.usuarioDesativa = usuarioDesativa;
	}

	public String getUsuarioUltAlt() {
		return usuarioUltAlt;
	}

	public void setUsuarioUltAlt(String usuarioUltAlt) {
		this.usuarioUltAlt = usuarioUltAlt;
	}	

}