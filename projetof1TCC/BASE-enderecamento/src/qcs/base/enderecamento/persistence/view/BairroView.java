package qcs.base.enderecamento.persistence.view;

import java.io.Serializable;

public class BairroView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idBairro;
	private String nome;
	private String abrev;
	private String nomeCidade;
	
	public Long getIdBairro() {
		return idBairro;
	}
	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAbrev() {
		return abrev;
	}
	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

}