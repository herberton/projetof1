package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class StatusDispositivoView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idStatusDispositivo;
	private String descricao;
	
	public Long getIdStatusDispositivo() {
		return idStatusDispositivo;
	}
	public void setIdStatusDispositivo(Long idStatusDispositivo) {
		this.idStatusDispositivo = idStatusDispositivo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}