	package qcs.base.modulo;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.Usuario;


/**
 * The persistent class for the PERFIL_FUNCIONALIDADE database table.
 * 
 */
@Entity
@Table(name="PERFIL_FUNCIONALIDADE")
public class PerfilFuncionalidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PerfilFuncionalidadeSequence", sequenceName="PERFIL_FUNCIONALIDADE_SEQ", allocationSize=5 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PerfilFuncionalidadeSequence")
	@Column(name="ID_PERFIL_FUNC",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idPerfilFunc;

	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false, columnDefinition="CHAR(1) NOT NULL")
	private String ativo;

	@NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ATIVACAO",nullable=false,columnDefinition="DATE NOT NULL")
	private Date dataAtivacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DESATIVACAO",nullable=true,columnDefinition="DATE NULL")
	private Date dataDesativacao;

    @Length(max=250,min=0)
    @Column(nullable=true,columnDefinition="VARCHAR2(250) NULL")
	private String observacao;

	//bi-directional many-to-one association to Funcionalidade
    @NotNull
    @ForeignKey(name="FUNC_PERF_FUNC_FK")
    @ManyToOne
	@JoinColumn(name="ID_FUNC",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Funcionalidade funcionalidade;

	//bi-directional many-to-one association to Perfil
    @NotNull
    @ForeignKey(name="PERFIL_FUNC_PERFIL_FK")
    @ManyToOne
	@JoinColumn(name="ID_PERFIL",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Perfil perfil;

	//uni-directional many-to-one association to Usuario
    @NotNull
    @ForeignKey(name="USU_ATIVA_PERF_FUNC_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ATIVA",nullable=false,columnDefinition="NUMBER(5,0) NOT NULL")
	private Usuario usuarioAtiva;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="USU_DESATIVA_PER_FUNC_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_DESATIVA",nullable=true,columnDefinition="NUMEBR(5,0) NOT NULL")
	private Usuario usuarioDesativa;

    public PerfilFuncionalidade() {
    }

	public Long getIdPerfilFunc() {
		return this.idPerfilFunc;
	}

	public void setIdPerfilFunc(Long idPerfilFunc) {
		this.idPerfilFunc = idPerfilFunc;
	}

	public String getAtivo() {
		return this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Date getDataAtivacao() {
		return this.dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataDesativacao() {
		return this.dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Funcionalidade getFuncionalidade() {
		return this.funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
	}
	
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Usuario getUsuarioAtiva() {
		return this.usuarioAtiva;
	}

	public void setUsuarioAtiva(Usuario usuarioAtiva) {
		this.usuarioAtiva = usuarioAtiva;
	}
	
	public Usuario getUsuarioDesativa() {
		return this.usuarioDesativa;
	}

	public void setUsuarioDesativa(Usuario usuarioDesativa) {
		this.usuarioDesativa = usuarioDesativa;
	}
	
}