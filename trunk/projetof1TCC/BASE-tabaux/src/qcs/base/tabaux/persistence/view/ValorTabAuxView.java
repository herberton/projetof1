package qcs.base.tabaux.persistence.view;

import java.io.Serializable;

public class ValorTabAuxView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idValTabAux;
	private String nomeTabAux;
	private String nome;
	private String abrev;
	private String observacao;
	
	public Long getIdValTabAux() {
		return idValTabAux;
	}
	
	public void setIdValTabAux(Long idValTabAux) {
		this.idValTabAux = idValTabAux;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeTabAux() {
		return nomeTabAux;
	}

	public void setNomeTabAux(String nomeTabAux) {
		this.nomeTabAux = nomeTabAux;
	}

	public String getAbrev() {
		return abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
