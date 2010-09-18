package qcs.base.negocio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the CLIENTE database table.
 * 
 */
@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="CLIENTE_IDCLIENTE_GENERATOR", sequenceName="SEQ_CLIENTE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_IDCLIENTE_GENERATOR")
	@Column(name="ID_CLIENTE")
	private Long idCliente;
	
	private double altura;
	
	@NotNull
	@Length(max=11, min=0)
	private String cpf;
	
    @Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;
    
	@NotNull
	@Length(max=50, min=0)
	private String nome;
	
	@Column(name="QTD_VISITAS")
	private Integer qtdVisitas;
	
	@Column(name="CELULAR")
	private Integer celular;	
	
	@NotNull
	@Length(max=9, min=0)
	private String rg;

	@NotNull
	@Length(max=1, min=0)
	private String sexo;

    public Cliente() {
    }

	public Long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public double getAltura() {
		return this.altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdVisitas() {
		return this.qtdVisitas;
	}

	public void setQtdVisitas(Integer qtdVisitas) {
		this.qtdVisitas = qtdVisitas;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}
	
	

}