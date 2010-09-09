package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;

public class LovBrinquedoView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idBrinquedo;
	private String nomeBrinquedo;
	private String observacao;
	
	public Long getIdBrinquedo() {
		return idBrinquedo;
	}
	public void setIdBrinquedo(Long idBrinquedo) {
		this.idBrinquedo = idBrinquedo;
	}
	public String getNomeBrinquedo() {
		return nomeBrinquedo;
	}
	public void setNomeBrinquedo(String nomeBrinquedo) {
		this.nomeBrinquedo = nomeBrinquedo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}