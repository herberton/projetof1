package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class FuncionalidadeView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idFuncionalidade;
	private String nomeFuncionalidade;
	private String descricaoFuncionalidade;
	private boolean visivel;
	private String modulo; 
	
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
	
	public String getDescricaoFuncionalidade() {
		return descricaoFuncionalidade;
	}
	
	public void setDescricaoFuncionalidade(String descricaoFuncionalidade) {
		this.descricaoFuncionalidade = descricaoFuncionalidade;
	}
	
	public boolean getVisivel() {
		return visivel;
	}
	
	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}