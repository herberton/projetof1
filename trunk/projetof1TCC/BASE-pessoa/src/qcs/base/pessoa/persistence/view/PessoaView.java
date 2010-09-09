package qcs.base.pessoa.persistence.view;

import java.io.Serializable;

public class PessoaView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idPessoa;
	private String nomePessoa;
	private String cnpj;
	private String cpf;
	private String sexo;
	private String tipoPessoa;
	private String ativo;
	private String rgNum;
	private String rgDig;
	
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
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public String getRgNum() {
		return rgNum;
	}
	public void setRgNum(String rgNum) {
		this.rgNum = rgNum;
	}
	public String getRgDig() {
		return rgDig;
	}
	public void setRgDig(String rgDig) {
		this.rgDig = rgDig;
	}
}