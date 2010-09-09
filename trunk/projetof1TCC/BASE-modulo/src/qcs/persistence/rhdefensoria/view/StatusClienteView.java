package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class StatusClienteView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idStatusCliente;
	private String descricao;
	
	public Long getIdStatusCliente() {
		return idStatusCliente;
	}
	public void setIdStatusCliente(Long idStatusCliente) {
		this.idStatusCliente = idStatusCliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}