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
 * The persistent class for the STATUS_CLIENTE database table.
 * 
 */
@Entity
@Table(name="STATUS_CLIENTE")
public class StatusCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="STATUS_CLIENTE_IDSTATUSCLIENTE_GENERATOR", sequenceName="SEQ_STATUS_CLIENTE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STATUS_CLIENTE_IDSTATUSCLIENTE_GENERATOR")
	@Column(name="ID_STATUS_CLIENTE")
	private Long idStatusCliente;

	@NotNull
	@Length(min=0,max=100)
	private String descricao;

    public StatusCliente() {
    }

	public Long getIdStatusCliente() {
		return this.idStatusCliente;
	}

	public void setIdStatusCliente(Long idStatusCliente) {
		this.idStatusCliente = idStatusCliente;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}