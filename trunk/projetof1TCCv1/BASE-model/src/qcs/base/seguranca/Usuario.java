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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import qcs.base.pessoa.Pessoa;


/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
@Table(uniqueConstraints={
		@UniqueConstraint(columnNames="LOGIN")})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="UsuarioSequence", sequenceName="USUARIO_SEQ", allocationSize=5)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UsuarioSequence")
	@Column(name="ID_USUARIO",unique=true,nullable=false,columnDefinition="NUMBER(5,0) NOT NULL")
	private Long idUsuario;

	@NotNull
	@Check(constraints="ativo in ('S','N')")
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'S'")
	private String ativo;
	@Transient
	private boolean ativoBoolean;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'S'")
	private String bloqueado;
	@Transient
	private boolean bloqueadoBoolean;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'N'")
	private String auditor;
	@Transient
	private boolean auditorBoolean;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'N'")
	private String master;
	@Transient
	private boolean masterBoolean;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1) default 'N'")
	private String administrador;
	@Transient
	private boolean administradorBoolean;

	@NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ATIVACAO",nullable=false,columnDefinition="DATE NULL")
	private Date dataAtivacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DESATIVACAO",columnDefinition="DATE NULL")
	private Date dataDesativacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ULT_ALTERACAO",columnDefinition="DATE NULL")
	private Date dataUltAlteracao;

    @Length(max=30,min=0)
	@Column(name="DICA_SENHA",columnDefinition="VARCHAR2(30) NULL")
	private String dicaSenha;

	//bi-directional many-to-one association to Pessoa
    @ForeignKey(name="PERFIL_USU_FK")
    @Index(columnNames={"ID_PERFIL"},name="PERFIL_USU_FK_I")
    @ManyToOne
	@JoinColumn(name="ID_PERFIL",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Perfil perfil;

    @NotNull
    @Length(max=12,min=1)
    @Column(nullable=false,unique=true,columnDefinition="VARCHAR2(12) NOT NULL",length=12)
	private String login;

    @Length(max=1000,min=0)
    @Column(nullable=true,columnDefinition="VARCHAR(1000) NULL")
	private String observacao;

    @Length(max=50,min=1)
    @Column(nullable=false,columnDefinition="VARCHAR(50) NOT NULL")
	private String senha;

	//bi-directional many-to-one association to PerfilUsuario
	@OneToMany(mappedBy="usuario")
	private List<PerfilUsuario> perfilUsuarios;

	//bi-directional many-to-one association to Pessoa
	@OneToMany(mappedBy="usuarioDesativa")
	private List<Pessoa> pessoas;

	//bi-directional many-to-one association to Pessoa
    @ForeignKey(name="PESSOA_USUARIO_FK")
    @Index(columnNames={"ID_PESSOA"},name="PESSOA_USUARIO_FK_I")
	@ManyToOne
	@JoinColumn(name="ID_PESSOA",nullable=true,columnDefinition="NUMBER(5,0) NULL")
	private Pessoa pessoa;

	//uni-directional many-to-one association to Usuario
    @NotNull
    @ForeignKey(name="USU_ATIVA_USU_FK")
    @Index(columnNames={"ID_USUARIO_ATIVA"},name="USU_ATIVA_USU_FK_I")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ATIVA",nullable=false,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioAtiva;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="USU_DESATIVACAO_FK")
    @Index(columnNames={"ID_USUARIO_DESATIVA"},name="USU_DESATIVACAO_FK_I")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_DESATIVA",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioDesativa;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="USU_ULT_ALTER_FK")
    @Index(columnNames={"ID_USUARIO_ALT"},name="USU_ULT_ALTER_FK_I")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ALT",nullable=true,columnDefinition="VARCHAR2(5,0)")
	private Usuario usuarioAlt;
    
    @Transient
    private Long idPerfil;

    public Usuario() {
    }
    
	public Long getIdPerfil() {
		if(idPerfil==null && perfil!=null)idPerfil=perfil.getIdPerfil();
		return idPerfil;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public String getDicaSenha() {
		return this.dicaSenha;
	}

	public void setDicaSenha(String dicaSenha) {
		this.dicaSenha = dicaSenha;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<PerfilUsuario> getPerfilUsuarios() {
		return this.perfilUsuarios;
	}

	public void setPerfilUsuarios(List<PerfilUsuario> perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}
	
	public List<Pessoa> getPessoas() {
		return this.pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public boolean getAuditorBoolean() {
		this.auditorBoolean = "S".equals(getAuditor());
		return auditorBoolean;
	}

	public void setAuditorBoolean(boolean auditorBoolean) {
		this.auditorBoolean = auditorBoolean;
		setAuditor((auditorBoolean)?"S":"N");
	}

	public boolean getMasterBoolean() {
		this.masterBoolean = "S".equals(getMaster());
		return masterBoolean;
	}

	public void setMasterBoolean(boolean masterBoolean) {
		this.masterBoolean = masterBoolean;
		setMaster((masterBoolean)?"S":"N");
	}

	public boolean getAdministradorBoolean() {
		this.administradorBoolean = "S".equals(getAdministrador());
		return administradorBoolean;
	}

	public void setAdministradorBoolean(boolean administradorBoolean) {
		this.administradorBoolean = administradorBoolean;
		setAdministrador((administradorBoolean)?"S":"N");
	}

	public boolean isBloqueadoBoolean() {
		this.bloqueadoBoolean = "S".equals(getBloqueado());
		return bloqueadoBoolean;
	}

	public void setBloqueadoBoolean(boolean bloqueadoBoolean) {
		this.bloqueadoBoolean = bloqueadoBoolean;
		setBloqueado((bloqueadoBoolean)?"S":"N");
	}

	public boolean getAtivoBoolean() {
		this.ativoBoolean = "S".equals(getAtivo());
		return ativoBoolean;
	}

	public void setAtivoBoolean(boolean ativoBoolean) {
		this.ativoBoolean = ativoBoolean;
		setAtivo((ativoBoolean)?"S":"N");
	}
}