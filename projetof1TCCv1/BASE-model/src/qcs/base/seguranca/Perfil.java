package qcs.base.seguranca;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * The persistent class for the PERFIL database table.
 * 
 */
@Entity
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PerfilSequence",sequenceName="PERFIL_SEQ", allocationSize=5)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PerfilSequence")
	@Column(name="ID_PERFIL",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idPerfil;

	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) NOT NULL")
	private String ativo;
	@Transient
	private boolean ativoTrans;

	@NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ATIVACAO",nullable=false,columnDefinition="DATE NOT NULL")
	private Date dataAtivacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DESATIVACAO",columnDefinition="DATE NULL")
	private Date dataDesativacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ULT_ALTERACAO",columnDefinition="DATE NULL")
	private Date dataUltAlteracao;

    @Length(max=250,min=0)
	@Column(name="DESCRICAO_PERFIL",columnDefinition="VARCHAR2(250) NULL")
	private String descricaoPerfil;

    @NotNull
    @Length(max=50,min=1)
	@Column(name="NOME_PERFIL",nullable=false,columnDefinition="VARCHAR2(50) NOT NULL")
	private String nomePerfil;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="PER_ACESSO_USU_ATIVA_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ATIVA",columnDefinition="NUMBER(5,0) NULL")
	private Usuario usuarioAtiva;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="PER_ACESSO_USU_DESATIVA_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_DESATIVA",columnDefinition="NUMBER(5,0) NULL")
	private Usuario usuarioDesativa;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="PER_ACESSO_USU_ULT_ALT_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ALT",columnDefinition="NUMBER(5,0) NULL")
	private Usuario usuarioAlt;

	//bi-directional many-to-one association to PerfilUsuario
	@OneToMany(mappedBy="perfil")
	private List<PerfilUsuario> perfilUsuarios;

    public Perfil() {
    }

	public Long getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
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

	public String getDescricaoPerfil() {
		return this.descricaoPerfil;
	}

	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}

	public String getNomePerfil() {
		return this.nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
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
	
	public Usuario getUsuarioAlt() {
		return this.usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}
	
	public List<PerfilUsuario> getPerfilUsuarios() {
		return this.perfilUsuarios;
	}

	public void setPerfilUsuarios(List<PerfilUsuario> perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}
	

	public Boolean getAtivoTrans() {	
		this.ativoTrans = "S".equals(ativo);
		return ativoTrans;			
	}

	public void setAtivoTrans(Boolean ativoTrans) {
		this.ativoTrans = ativoTrans;
		setAtivo((ativoTrans)?"S":"N");	
	}
	
}