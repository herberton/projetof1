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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the HISTORICO_CLIENTE database table.
 * 
 */
@Entity
@Table(name="HISTORICO_CLIENTE")
public class HistoricoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="HISTORICO_CLIENTE_IDHISTORICOCLINTE_GENERATOR", sequenceName="SEQ_HISTORICO_CLIENTE ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HISTORICO_CLIENTE_IDHISTORICOCLINTE_GENERATOR")
	@Column(name="ID_HISTORICO_CLINTE")
	private Long idHistoricoClinte;

	@NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_ENTRADA_PARQUE")
	private Date dataHoraEntradaParque;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_HORA_SAIDA_PARQUE")
	private Date dataHoraSaidaParque;

    @Length(min=0,max=150)
	private String observacao;

	//uni-directional many-to-one association to Cliente
    @ManyToOne
	@JoinColumn(name="ID_CLIENTE")
	private Cliente cliente;

	//uni-directional many-to-one association to Dispositivo
    @ManyToOne
	@JoinColumn(name="ID_DISPOSITIVO")
	private Dispositivo dispositivo;

	//uni-directional many-to-one association to StatusDispositivo
    @ManyToOne
	@JoinColumn(name="ID_STATUS_DISPOSITIVO")
	private StatusDispositivo statusDispositivo;

    public HistoricoCliente() {
    }

	public Long getIdHistoricoClinte() {
		return this.idHistoricoClinte;
	}

	public void setIdHistoricoClinte(Long idHistoricoClinte) {
		this.idHistoricoClinte = idHistoricoClinte;
	}

	public Date getDataHoraEntradaParque() {
		return this.dataHoraEntradaParque;
	}

	public void setDataHoraEntradaParque(Date dataHoraEntradaParque) {
		this.dataHoraEntradaParque = dataHoraEntradaParque;
	}

	public Date getDataHoraSaidaParque() {
		return this.dataHoraSaidaParque;
	}

	public void setDataHoraSaidaParque(Date dataHoraSaidaParque) {
		this.dataHoraSaidaParque = dataHoraSaidaParque;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Dispositivo getDispositivo() {
		return this.dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	public StatusDispositivo getStatusDispositivo() {
		return this.statusDispositivo;
	}

	public void setStatusDispositivo(StatusDispositivo statusDispositivo) {
		this.statusDispositivo = statusDispositivo;
	}
	
}