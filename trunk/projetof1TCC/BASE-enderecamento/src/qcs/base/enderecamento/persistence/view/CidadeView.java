package qcs.base.enderecamento.persistence.view;

import java.io.Serializable;

public class CidadeView implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idCidade;
	private String Nome;
	private String uf;
	private boolean selecionado;
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public boolean isSelecionado() {
		return selecionado;
	}
	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

}
