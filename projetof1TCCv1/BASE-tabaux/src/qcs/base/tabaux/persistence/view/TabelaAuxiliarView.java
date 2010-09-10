package qcs.base.tabaux.persistence.view;

import java.io.Serializable;

public class TabelaAuxiliarView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idTabAux;
	private String descricao;
	private String nome;

	public Long getIdTabAux() {
		return idTabAux;
	}
	public void setIdTabAux(Long idTabAux) {
		this.idTabAux = idTabAux;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	

}
