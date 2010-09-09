package qcs.base.seguranca;

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


/**
 * The persistent class for the PERFIL_USUARIO database table.
 * 
 */
@Entity
@Table(name="PERFIL_USUARIO")
public class PerfilUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PerfilUsuarioSequence", sequenceName="PERFIL_USUARIO_SEQ", allocationSize=5 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PerfilUsuarioSequence")
	@Column(name="ID_PERFIL_USUARIO",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idPerfilUsuario;

	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'S'")
	private String ativo;

	@NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ATIVACAO",nullable=false,columnDefinition="DATE")
	private Date dataAtivacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DESATIVACAO",nullable=true,columnDefinition="DATE")
	private Date dataDesativacao;
    
    @NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ULT_ALTERACAO",nullable=false,columnDefinition="DATE")
	private Date dataUltAlteracao;

    @Column(nullable=true,columnDefinition="VARCHAR2(1000)")
	private String observacao;

	//bi-directional many-to-one association to Perfil
    @NotNull
    @ForeignKey(name="PERFIL_USU_PERFIL_ACESSO_FK")
    @ManyToOne
	@JoinColumn(name="ID_PERFIL",nullable=false,columnDefinition="NUMBER(3,0)")
	private Perfil perfil;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="PERFIL_ULT_ALTER_USUARIO_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ALT",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioAlt;

	//uni-directional many-to-one association to Usuario
    @NotNull
    @ForeignKey(name="USU_ATIVA_PERFIL_USU_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ATIVA",nullable=false,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioAtiva;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="USU_DESATIVA_PERF_USU_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_DESATIVA",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioDesativa;

	//bi-directional many-to-one association to Usuario
    @NotNull
    @ForeignKey(name="USU_PERF_USU_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO",nullable=false,columnDefinition="NUMBER(5,0)")
	private Usuario usuario;

    public PerfilUsuario() {
    }

	public Long getIdPerfilUsuario() {
		return this.idPerfilUsuario;
	}

	public void setIdPerfilUsuario(Long idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
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

	public Date getDataUltAlteracao() {
		return this.dataUltAlteracao;
	}

	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Usuario getUsuarioAlt() {
		return this.usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
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
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}