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
 * The persistent class for the STATUS_DISPOSITIVO database table.
 * 
 */
@Entity
@Table(name="STATUS_DISPOSITIVO")
public class StatusDispositivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="STATUS_DISPOSITIVO_IDSTATUSDISPOSITIVO_GENERATOR", sequenceName="SEQ_STATUS_DISPOSITIVO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STATUS_DISPOSITIVO_IDSTATUSDISPOSITIVO_GENERATOR")
	@Column(name="ID_STATUS_DISPOSITIVO")
	private Long idStatusDispositivo;

	@NotNull
	@Length(min=0,max=100)
	private String descricao;

    public StatusDispositivo() {
    }

	public Long getIdStatusDispositivo() {
		return this.idStatusDispositivo;
	}

	public void setIdStatusDispositivo(Long idStatusDispositivo) {
		this.idStatusDispositivo = idStatusDispositivo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}