package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;


public class ClienteView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idCliente;
	private String nome;
	private String rg;
	private String cpf;
	private Integer celular;
	private Long dispositivoAssoc;
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Integer getCelular() {
		return celular;
	}
	public void setCelular(Integer celular) {
		this.celular = celular;
	}
	public Long getdispositivoAssoc() {
		return dispositivoAssoc;
	}
	public void setdispositivoAssoc(Long dispositivoAssoc) {
		this.dispositivoAssoc = dispositivoAssoc;
	}

	
}