package qcs.base.seguranca.persistence.view;

import java.io.Serializable;

public class PerfilView implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nomePerfilAcesso;
	private String descPerfilAcesso;
	private Long idPerfil;
	private String ativo;
	
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public String getNomePerfilAcesso() {
		return nomePerfilAcesso;
	}
	public void setNomePerfilAcesso(String nomePerfilAcesso) {
		this.nomePerfilAcesso = nomePerfilAcesso;
	}
	public String getDescPerfilAcesso() {
		return descPerfilAcesso;
	}
	public void setDescPerfilAcesso(String descPerfilAcesso) {
		this.descPerfilAcesso = descPerfilAcesso;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	

}