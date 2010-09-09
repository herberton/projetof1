package qcs.base.seguranca.persistence.view;

import java.io.Serializable;


public class PerfilFuncionalidadeView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idPerfilFunc;
	private Long idFuncionalidade;
	private String nomeFuncionalidade;
	private Long idPerfil;
	private String nomePerfil;
	private String ativo;
	
	
	public Long getIdFuncionalidade() {
		return idFuncionalidade;
	}
	public void setIdFuncionalidade(Long idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}
	public String getNomeFuncionalidade() {
		return nomeFuncionalidade;
	}
	public void setNomeFuncionalidade(String nomeFuncionalidade) {
		this.nomeFuncionalidade = nomeFuncionalidade;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getNomePerfil() {
		return nomePerfil;
	}
	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public Long getIdPerfilFunc() {
		return idPerfilFunc;
	}
	public void setIdPerfilFunc(Long idPerfilFunc) {
		this.idPerfilFunc = idPerfilFunc;
	}

}