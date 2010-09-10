package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;

public class ListOfValuesModuloSistemaView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idModulo;
	private String nome;
	private String descritivo;
	
	public Long getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescritivo() {
		return descritivo;
	}
	public void setDescritivo(String descritivo) {
		this.descritivo = descritivo;
	}

}
