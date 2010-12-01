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
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;


/**
 * The persistent class for the FILA database table.
 * 
 */
@Entity
public class Fila implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="FILA_IDFILA_GENERATOR", sequenceName="SEQ_FILA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILA_IDFILA_GENERATOR")
	@Column(name="ID_FILA")
	private Long idFila;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENTRADA_FILA_FISICA")
	private Date dataHoraEntradaFilaFisica;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENTRADA_FILA_VIRTUAL")
	private Date dataHoraEntradaFilaVirtual;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_SAIDA_BRINQUEDO")
	private Date dataHoraSaidaBrinquedo;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HOTA_ENVIO_MENSAGEM")
	private Date dataHotaEnvioMensagem;

	//uni-directional many-to-one association to Brinquedo
    @ManyToOne
    //@NotNull
	@JoinColumn(name="ID_BRINQUEDO")
	private Brinquedo brinquedo;

	//uni-directional many-to-one association to Dispositivo
    @ManyToOne
    @NotNull
	@JoinColumn(name="ID_DISPOSITIVO")
	private Dispositivo dispositivo;

	//uni-directional many-to-one association to StatusCliente
    @ManyToOne
    @NotNull
	@JoinColumn(name="ID_STATUS_CLIENTE")
	private StatusCliente statusCliente;

    public Fila() {
    }

	public Long getIdFila() {
		return this.idFila;
	}

	public void setIdFila(Long idFila) {
		this.idFila = idFila;
	}

	public Date getDataHoraEntradaFilaFisica() {
		return this.dataHoraEntradaFilaFisica;
	}

	public void setDataHoraEntradaFilaFisica(Date dataHoraEntradaFilaFisica) {
		this.dataHoraEntradaFilaFisica = dataHoraEntradaFilaFisica;
	}

	public Date getDataHoraEntradaFilaVirtual() {
		return this.dataHoraEntradaFilaVirtual;
	}

	public void setDataHoraEntradaFilaVirtual(Date dataHoraEntradaFilaVirtual) {
		this.dataHoraEntradaFilaVirtual = dataHoraEntradaFilaVirtual;
	}

	public Date getDataHoraSaidaBrinquedo() {
		return this.dataHoraSaidaBrinquedo;
	}

	public void setDataHoraSaidaBrinquedo(Date dataHoraSaidaBrinquedo) {
		this.dataHoraSaidaBrinquedo = dataHoraSaidaBrinquedo;
	}

	public Date getDataHotaEnvioMensagem() {
		return this.dataHotaEnvioMensagem;
	}

	public void setDataHotaEnvioMensagem(Date dataHotaEnvioMensagem) {
		this.dataHotaEnvioMensagem = dataHotaEnvioMensagem;
	}

	public Brinquedo getBrinquedo() {
		return this.brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}
	
	public Dispositivo getDispositivo() {
		return this.dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public StatusCliente getStatusCliente() {
		return this.statusCliente;
	}

	public void setStatusCliente(StatusCliente statusCliente) {
		this.statusCliente = statusCliente;
	}
	
}