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
 * The persistent class for the CATRACA database table.
 * 
 */
@Entity
public class Catraca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="CATRACA_IDCATRACA_GENERATOR", sequenceName="SEQ_CATRACA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATRACA_IDCATRACA_GENERATOR")
	@Column(name="ID_CATRACA")
	private Long idCatraca;
	
	
	@Length(max=100,min=0)
	private String descricao;
	
	
	@Length(max=150,min=0)
	private String localizacao;

	//uni-directional many-to-one association to Brinquedo
    @ManyToOne
	@JoinColumn(name="ID_BRINQUEDO")
	private Brinquedo brinquedo;

    public Catraca() {
    }

	public Long getIdCatraca() {
		return this.idCatraca;
	}

	public void setIdCatraca(Long idCatraca) {
		this.idCatraca = idCatraca;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocalizacao() {
		return this.localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Brinquedo getBrinquedo() {
		return this.brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}
	
}