package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class StatusBrinquedoView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idStatusBrinquedo;
	private String descricao;
	
	public Long getIdStatusBrinquedo() {
		return idStatusBrinquedo;
	}
	public void setIdStatusBrinquedo(Long idStatusBrinquedo) {
		this.idStatusBrinquedo = idStatusBrinquedo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}