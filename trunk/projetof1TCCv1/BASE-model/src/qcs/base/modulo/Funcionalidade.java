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
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Digits;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import qcs.base.seguranca.PerfilFuncionalidade;


/**
 * The persistent class for the FUNCIONALIDADE database table.
 * 
 */
@Entity
public class Funcionalidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FuncionalidadeSequence")
	@SequenceGenerator(sequenceName="FUNCIONALIDADE_SEQ", name="FuncionalidadeSequence", allocationSize=1)
	@Digits(integerDigits=3,fractionalDigits=0)
	@Column(name="ID_FUNC",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idFunc;

	@Length(max=250,min=0)
	@Column(nullable=true,columnDefinition="VARCHAR(250) NULL")
	private String descricao;

	@NotNull
	@Length(max=50,min=1)
	@Column(nullable=false,columnDefinition="VARCHAR(50) NOT NULL")
	private String nome;

	@Digits(integerDigits=2,fractionalDigits=0)
	@Column(name="ORDEM_FUNC",nullable=true,columnDefinition="NUMBER(2,0) NULL")
	private Integer ordemFunc;

	@NotNull
	@Length(max=1,min=1)
	@Column(name="VISIVEL_MENU",nullable=false,columnDefinition="CHAR(1) NOT NULL")
	private String visivelMenu;

	@Length(max=50,min=0)
	@Column(nullable=false,columnDefinition="VARCHAR(50) NOT NULL")
	private String acesso;

	@Transient
	private boolean visivelMenuBoolean;

	//bi-directional many-to-one association to ModuloSistema
	@NotNull
	@ForeignKey(name="MODULO_FUNC_FK")
	@ManyToOne
	@JoinColumn(name="ID_MODULO",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private ModuloSistema moduloSistema;

	//bi-directional many-to-one association to PerfilFuncionalidade
	@OneToMany(mappedBy="funcionalidade")
	private List<PerfilFuncionalidade> perfilFuncionalidades;

	public Funcionalidade() {
	}

	public Long getIdFunc() {
		return this.idFunc;
	}

	public void setIdFunc(Long idFunc) {
		this.idFunc = idFunc;
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

	public Integer getOrdemFunc() {
		return this.ordemFunc;
	}

	public void setOrdemFunc(Integer ordemFunc) {
		this.ordemFunc = ordemFunc;
	}

	public String getVisivelMenu() {
		return this.visivelMenu;
	}

	public void setVisivelMenu(String visivelMenu) {
		this.visivelMenu = visivelMenu;
	}

	public ModuloSistema getModuloSistema() {
		return this.moduloSistema;
	}

	public void setModuloSistema(ModuloSistema moduloSistema) {
		this.moduloSistema = moduloSistema;
	}

	public List<PerfilFuncionalidade> getPerfilFuncionalidades() {
		return this.perfilFuncionalidades;
	}

	public void setPerfilFuncionalidades(List<PerfilFuncionalidade> perfilFuncionalidades) {
		this.perfilFuncionalidades = perfilFuncionalidades;
	}

	public boolean isVisivelMenuBoolean() {
		if(visivelMenu == null) this.visivelMenuBoolean = false;
		else if(visivelMenu.equals("S")) this.visivelMenuBoolean = true;
		else this.visivelMenuBoolean = false;
		return visivelMenuBoolean;
	}

	public void setVisivelMenuBoolean(boolean visivelMenuBoolean) {
		this.visivelMenuBoolean = visivelMenuBoolean;
		this.visivelMenu = (visivelMenuBoolean)?"S":"N";
	}

	public String getAcesso() {
		return acesso;
	}

	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}
}