package qcs.base.negocio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the BRINQUEDO database table.
 * 
 */
@Entity
public class Brinquedo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BRINQUEDO_IDBRINQUEDO_GENERATOR", sequenceName="SEQ_BRINQUEDO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BRINQUEDO_IDBRINQUEDO_GENERATOR")
	@Column(name="ID_BRINQUEDO")
	private Long idBrinquedo;

	@Column(name="ESTIMATIVA_TEMPO_FILA")
	private Integer estimativaTempoFila;
	
	@NotNull
	@Length(max=50,min=0)
	private String nome;

	@Length(max=100,min=0)
	private String observacao;

	@Column(name="QTD_MAX_PESSOAS_FILA_FISICA")
	private Integer qtdMaxPessoasFilaFisica;

	@Column(name="QTD_PESSOAS_FILA_FISICA")
	private Integer qtdPessoasFilaFisica;

	@Column(name="TEMPO_MENSAGEM")
	private Integer tempoMensagem;

	//uni-directional many-to-one association to StatusBrinquedo
    @ManyToOne
	@JoinColumn(name="ID_STATUS_BRINQUEDO")
	private StatusBrinquedo statusBrinquedo;

    public Brinquedo() {
    }

	public Long getIdBrinquedo() {
		return this.idBrinquedo;
	}

	public void setIdBrinquedo(Long idBrinquedo) {
		this.idBrinquedo = idBrinquedo;
	}

	public Integer getEstimativaTempoFila() {
		return this.estimativaTempoFila;
	}

	public void setEstimativaTempoFila(Integer estimativaTempoFila) {
		this.estimativaTempoFila = estimativaTempoFila;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getQtdMaxPessoasFilaFisica() {
		return this.qtdMaxPessoasFilaFisica;
	}

	public void setQtdMaxPessoasFilaFisica(Integer qtdMaxPessoasFilaFisica) {
		this.qtdMaxPessoasFilaFisica = qtdMaxPessoasFilaFisica;
	}

	public Integer getQtdPessoasFilaFisica() {
		return this.qtdPessoasFilaFisica;
	}

	public void setQtdPessoasFilaFisica(Integer qtdPessoasFilaFisica) {
		this.qtdPessoasFilaFisica = qtdPessoasFilaFisica;
	}

	public Integer getTempoMensagem() {
		return this.tempoMensagem;
	}

	public void setTempoMensagem(Integer tempoMensagem) {
		this.tempoMensagem = tempoMensagem;
	}

	public StatusBrinquedo getStatusBrinquedo() {
		return this.statusBrinquedo;
	}

	public void setStatusBrinquedo(StatusBrinquedo statusBrinquedo) {
		this.statusBrinquedo = statusBrinquedo;
	}
	
}