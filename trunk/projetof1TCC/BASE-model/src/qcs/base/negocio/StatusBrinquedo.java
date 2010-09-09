package qcs.base.negocio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the STATUS_BRINQUEDO database table.
 * 
 */
@Entity
@Table(name="STATUS_BRINQUEDO")
public class StatusBrinquedo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="STATUS_BRINQUEDO_IDSTATUSBRINQUEDO_GENERATOR", sequenceName="SEQ_STATUS_BRINQUEDO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STATUS_BRINQUEDO_IDSTATUSBRINQUEDO_GENERATOR")
	@Column(name="ID_STATUS_BRINQUEDO")
	private Long idStatusBrinquedo;

	@NotNull
	@Length(min=0,max=100)
	private String descricao;

    public StatusBrinquedo() {
    }

	public Long getIdStatusBrinquedo() {
		return this.idStatusBrinquedo;
	}

	public void setIdStatusBrinquedo(Long idStatusBrinquedo) {
		this.idStatusBrinquedo = idStatusBrinquedo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}