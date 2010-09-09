package qcs.base.modulo;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Digits;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the MODULO_SISTEMA database table.
 * 
 */
@Entity
@Table(name="MODULO_SISTEMA")
public class ModuloSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ModuloSistemaSequence")
	@SequenceGenerator(sequenceName="MODULO_SISTEMA_SEQ", name="ModuloSistemaSequence", allocationSize=1)
	@Column(name="ID_MODULO",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idModulo;

	@Length(max=250,min=0)
	@Column(nullable=true,columnDefinition="VARCHAR2(250) NULL")
	private String descricao;

	@NotNull
	@Length(max=50,min=1)
	@Column(nullable=false,columnDefinition="VARCHAR2(50) NOT NULL")
	private String nome;

	@Digits(integerDigits=2,fractionalDigits=0)
	@Column(nullable=true,columnDefinition="NUMBER(2,0) NULL")
	private Integer posicao;

	//bi-directional many-to-one association to Funcionalidade
	@OneToMany(mappedBy="moduloSistema")
	private List<Funcionalidade> funcionalidades;

	//bi-directional many-to-one association to ModuloSistema
    @ForeignKey(name="MODULO_SUB_MODULO_FK")
	@ManyToOne
	@JoinColumn(name="ID_MODULO_PAI",nullable=true,columnDefinition="NUMBER(3,0) NULL")
	private ModuloSistema moduloPai;

	//bi-directional many-to-one association to ModuloSistema
	@OneToMany(mappedBy="moduloPai")
	private List<ModuloSistema> modulosFilho;

	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) NOT NULL")
	private String principal;
	
	@Transient
	private boolean principalBoolean;
	
    public ModuloSistema() {
    }

	public Long getIdModulo() {
		return this.idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPosicao() {
		return this.posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public List<Funcionalidade> getFuncionalidades() {
		return this.funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	
	public ModuloSistema getModuloPai() {
		return this.moduloPai;
	}

	public void setModuloPai(ModuloSistema moduloPai) {
		this.moduloPai = moduloPai;
	}
	
	public List<ModuloSistema> getModulosFilho() {
		return this.modulosFilho;
	}

	public void setModulosFilho(List<ModuloSistema> modulosFilho) {
		this.modulosFilho = modulosFilho;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public boolean isPrincipalBoolean() {
		if(principal == null) this.principalBoolean = false;
		else if(principal.equals("S")) this.principalBoolean = true;
		else this.principalBoolean = false;
		return principalBoolean;
	}

	public void setPrincipalBoolean(boolean principalBoolean) {
		this.principalBoolean = principalBoolean;
		this.principal = (principalBoolean)?"S":"N";
	}
	
}