package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;
import java.util.Date;


public class ClienteView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idCliente;
	private String nome;
	private Date dataNascimento;
	private Integer qtdVisitas;
	private String rg;
	private String cpf;
	
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
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
	public Integer getQtdVisitas() {
		return qtdVisitas;
	}
	public void setQtdVisitas(Integer qtdVisitas) {
		this.qtdVisitas = qtdVisitas;
	}
	
}