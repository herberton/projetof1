package qcs.base.negocio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the DISPOSITIVO database table.
 * 
 */
@Entity
public class Dispositivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="DISPOSITIVO_IDDISPOSITIVO_GENERATOR", sequenceName="SEQ_DISPOSITIVO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DISPOSITIVO_IDDISPOSITIVO_GENERATOR")
	@Column(name="ID_DISPOSITIVO")
	private Long idDispositivo;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
    
    @Length(min=0,max=15)
	private String ip;

	//uni-directional many-to-one association to Cliente
    @OneToOne
	@JoinColumn(name="ID_CLIENTE")
	private Cliente cliente;

	//uni-directional many-to-one association to StatusDispositivo
    @ManyToOne
	@JoinColumn(name="ID_STATUS_DISPOSITIVO")
	private StatusDispositivo statusDispositivo;

	@Column(name="CELULAR")
	private Integer celular;
	
	@Column(name="ID_RFID")
	private String idRfid;
    
    
    public Dispositivo() {
    }

	public Long getIdDispositivo() {
		return this.idDispositivo;
	}

	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public StatusDispositivo getStatusDispositivo() {
		return this.statusDispositivo;
	}

	public void setStatusDispositivo(StatusDispositivo statusDispositivo) {
		this.statusDispositivo = statusDispositivo;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public String getIdRfid() {
		return idRfid;
	}

	public void setIdRfid(String idRfid) {
		this.idRfid = idRfid;
	}
	
	
	
}