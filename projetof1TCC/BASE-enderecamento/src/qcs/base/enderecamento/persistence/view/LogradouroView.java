package qcs.base.enderecamento.persistence.view;

import java.io.Serializable;

public class LogradouroView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idLogradouro;
	private String cep;
	private String logradouro;
	private String bairro;	
	private String cidade;
	private String uf;
	private String inativo;
	
	public Long getIdLogradouro() {
		return idLogradouro;
	}
	public void setIdLogradouro(Long idLogradouro) {
		this.idLogradouro = idLogradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getInativo() {
		return inativo;
	}
	public void setInativo(String inativo) {
		this.inativo = inativo;
	}
	
}
