package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;
import java.util.Date;


public class HistoricoClienteView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id_historico_cliente;
	private Date data_hora_entrada_parque;
	private Date data_hora_saida_parque;
	private String observacao;
	private Long idDispositivo;
	private Long idStatusDispositivo;
	private Long idCliente;
	
	public Long getId_historico_cliente() {
		return id_historico_cliente;
	}
	public void setId_historico_cliente(Long idHistoricoCliente) {
		id_historico_cliente = idHistoricoCliente;
	}
	public Date getData_hora_entrada_parque() {
		return data_hora_entrada_parque;
	}
	public void setData_hora_entrada_parque(Date dataHoraEntradaParque) {
		data_hora_entrada_parque = dataHoraEntradaParque;
	}
	public Date getData_hora_saida_parque() {
		return data_hora_saida_parque;
	}
	public void setData_hora_saida_parque(Date dataHoraSaidaParque) {
		data_hora_saida_parque = dataHoraSaidaParque;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Long getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public Long getIdStatusDispositivo() {
		return idStatusDispositivo;
	}
	public void setIdStatusDispositivo(Long idStatusDispositivo) {
		this.idStatusDispositivo = idStatusDispositivo;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

}