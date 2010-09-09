package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class CatracaView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idCatraca;
	private String descricao;
	private String localizacao;
	private String nomeBrinquedo;
	
	public Long getIdCatraca() {
		return idCatraca;
	}
	public void setIdCatraca(Long idCatraca) {
		this.idCatraca = idCatraca;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public String getNomeBrinquedo() {
		return nomeBrinquedo;
	}
	public void setNomeBrinquedo(String nomeBrinquedo) {
		this.nomeBrinquedo = nomeBrinquedo;
	}
	
	

	

}