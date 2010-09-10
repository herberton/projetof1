package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;

public class ModuloSistemaView implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idModulo;
	private String nomeModulo;
	private Integer posicao;
	private String descricao;
	private boolean principal;
	
	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	
	public String getNomeModulo() {
		return nomeModulo;
	}
	
	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}
	
	public Integer getPosicao() {
		return posicao;
	}
	
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

}
