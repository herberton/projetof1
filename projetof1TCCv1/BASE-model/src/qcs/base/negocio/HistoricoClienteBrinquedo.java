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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;


/**
 * The persistent class for the HISTORICO_CLIENTE_BRINQUEDO database table.
 * 
 */
@Entity
@Table(name="HISTORICO_CLIENTE_BRINQUEDO")
public class HistoricoClienteBrinquedo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="HISTORICO_CLIENTE_BRINQUEDO_IDHISTORICOCLIENTEBRINQUEDO_GENERATOR", sequenceName="SEQ_HIST_CLIENTE_BRINQUEDO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HISTORICO_CLIENTE_BRINQUEDO_IDHISTORICOCLIENTEBRINQUEDO_GENERATOR")
	@Column(name="ID_HISTORICO_CLIENTE_BRINQUEDO")
	private Long idHistoricoClienteBrinquedo;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENTRADA_FILA_FISICA")
	private Date dataHoraEntradaFilaFisica;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENTRADA_FILA_VIRTUAL")
	private Date dataHoraEntradaFilaVirtual;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENVIO_MENSAGEM")
	private Date dataHoraEnvioMensagem;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_SAIDA_BRINQUEDO")
	private Date dataHoraSaidaBrinquedo;

	//uni-directional many-to-one association to Brinquedo
    @ManyToOne
	@JoinColumn(name="ID_BRINQUEDO")
	private Brinquedo brinquedo;

	//uni-directional many-to-one association to Cliente
    @ManyToOne
	@JoinColumn(name="ID_CLIENTE")
	private Cliente cliente;

    public HistoricoClienteBrinquedo() {
    }

	public Long getIdHistoricoClienteBrinquedo() {
		return this.idHistoricoClienteBrinquedo;
	}

	public void setIdHistoricoClienteBrinquedo(Long idHistoricoClienteBrinquedo) {
		this.idHistoricoClienteBrinquedo = idHistoricoClienteBrinquedo;
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

	public Date getDataHoraEnvioMensagem() {
		return this.dataHoraEnvioMensagem;
	}

	public void setDataHoraEnvioMensagem(Date dataHoraEnvioMensagem) {
		this.dataHoraEnvioMensagem = dataHoraEnvioMensagem;
	}

	public Date getDataHoraSaidaBrinquedo() {
		return this.dataHoraSaidaBrinquedo;
	}

	public void setDataHoraSaidaBrinquedo(Date dataHoraSaidaBrinquedo) {
		this.dataHoraSaidaBrinquedo = dataHoraSaidaBrinquedo;
	}

	public Brinquedo getBrinquedo() {
		return this.brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}