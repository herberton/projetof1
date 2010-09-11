package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class BrinquedoView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idBrinquedo;
	private String nome;
	private Integer tempoMensagem;
	private Integer estimativaTempoFila;
	private Integer qntFilaFisica;
	private Integer qntMaxFilaFisica;
	private Long statusBrinquedo;
	private String descricaoStatusBrinquedo;
	
	public Long getIdBrinquedo() {
		return idBrinquedo;
	}
	public void setIdBrinquedo(Long idBrinquedo) {
		this.idBrinquedo = idBrinquedo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getTempoMensagem() {
		return tempoMensagem;
	}
	public void setTempoMensagem(Integer tempoMensagem) {
		this.tempoMensagem = tempoMensagem;
	}
	public Integer getEstimativaTempoFila() {
		return estimativaTempoFila;
	}
	public void setEstimativaTempoFila(Integer estimativaTempoFila) {
		this.estimativaTempoFila = estimativaTempoFila;
	}
	public Integer getQntFilaFisica() {
		return qntFilaFisica;
	}
	public void setQntFilaFisica(Integer qntFilaFisica) {
		this.qntFilaFisica = qntFilaFisica;
	}
	public Integer getQntMaxFilaFisica() {
		return qntMaxFilaFisica;
	}
	public void setQntMaxFilaFisica(Integer qntMaxFilaFisica) {
		this.qntMaxFilaFisica = qntMaxFilaFisica;
	}
	public Long getStatusBrinquedo() {
		return statusBrinquedo;
	}
	public void setStatusBrinquedo(Long statusBrinquedo) {
		this.statusBrinquedo = statusBrinquedo;
	}
	public String getDescricaoStatusBrinquedo() {
		return descricaoStatusBrinquedo;
	}
	public void setDescricaoStatusBrinquedo(String descricaoStatusBrinquedo) {
		this.descricaoStatusBrinquedo = descricaoStatusBrinquedo;
	}
	
	
}