package qcs.base.pessoa.persistence.view;

import java.io.Serializable;

public class LovPessoaView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idPessoa;
	private String nomePessoa;
	private String cpf;
	private String cnpj; 
	private String tipPes;
	
	public Long getIdPessoa() {
		return idPessoa;
	}
	
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public String getNomePessoa() {
		return nomePessoa;
	}
	
	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getTipPes() {
		return tipPes;
	}
	
	public void setTipPes(String tipPes) {
		this.tipPes = tipPes;
	}
}