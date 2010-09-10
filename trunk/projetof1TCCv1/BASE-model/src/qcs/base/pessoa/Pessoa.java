package qcs.base.pessoa;

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

import qcs.base.seguranca.Usuario;
import qcs.base.tabaux.ValorTabAux;


/**
 * The persistent class for the PESSOA database table.
 * 
 */
@Entity
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PessoaSequence", sequenceName="PESSOA_SEQ", allocationSize=5)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PessoaSequence")
	@Column(name="ID_PESSOA",nullable=false,columnDefinition="NUMBER(3,0) NOT NULL")
	private Long idPessoa;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1)")
	private String ativo;

	@Length(max=14,min=0)
	@Column(nullable=true,columnDefinition="VARCHAR2(14)")
	private String cnpj;

	@Length(max=11,min=0)
	@Column(nullable=true,columnDefinition="VARCHAR2(11)")
	private String cpf;
	
	@Length(max=1,min=0)
	@Column(columnDefinition="CHAR(1) NULL")
	private String sexo;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ADMISSAO",nullable=true,columnDefinition="DATE")
	private Date dataAdmissao;

    @NotNull
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ATIVACAO",nullable=false,columnDefinition="DATE")
	private Date dataAtivacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DESATIVACAO",nullable=true,columnDefinition="DATE")
	private Date dataDesativacao;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASCIMENTO",nullable=true,columnDefinition="DATE")
	private Date dataNascimento;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_ULT_ALTERACAO",nullable=true,columnDefinition="DATE")
	private Date dataUltAlteracao;

    @NotNull
    @ForeignKey(name="TAB_AUX_TIPO_PES_PESSOA_FK")
    @ManyToOne
	@JoinColumn(name="ID_TIPO_PES",nullable=false,columnDefinition="NUMBER(9,0)")
	private ValorTabAux tipoPessoa;
    
    @Transient
    private Long idTipoPessoa;

    @NotNull
    @Length(max=50,min=1)
	@Column(name="NOME_PESSOA",nullable=false,columnDefinition="VARCHAR2(50)")
	private String nomePessoa;

    @Length(max=10,min=0)
	@Column(name="ORGAO_EMISSAO_RG",nullable=true,columnDefinition="VARCHAR2(10)")
	private String orgaoEmissaoRg;

    @Length(max=1,min=0)
	@Column(name="RG_DIG",nullable=true,columnDefinition="VARCHAR2(1)")
	private String rgDig;

    @Length(max=10,min=0)
	@Column(name="RG_NUM",nullable=true,columnDefinition="10")
	private String rgNum;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="PESSOA_USUARIO_ATIVA_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ATIVA",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioAtiva;

	//uni-directional many-to-one association to Usuario
    @ForeignKey(name="USUARIO_PESSOA_ALT_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_ALT",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioAlt;

	//bi-directional many-to-one association to Usuario
    @ForeignKey(name="USUARIO_PESSOA_DESATIVA_FK")
    @ManyToOne
	@JoinColumn(name="ID_USUARIO_DESATIVA",nullable=true,columnDefinition="NUMBER(5,0)")
	private Usuario usuarioDesativa;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="pessoa")
	private List<Usuario> usuarios;

    public Pessoa() {
    }

	public Long getIdPessoa() {
		return this.idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getAtivo() {
		return this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataAdmissao() {
		return this.dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
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

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataUltAlteracao() {
		return this.dataUltAlteracao;
	}

	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}

	public String getNomePessoa() {
		return this.nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getOrgaoEmissaoRg() {
		return this.orgaoEmissaoRg;
	}

	public void setOrgaoEmissaoRg(String orgaoEmissaoRg) {
		this.orgaoEmissaoRg = orgaoEmissaoRg;
	}

	public String getRgDig() {
		return this.rgDig;
	}

	public void setRgDig(String rgDig) {
		this.rgDig = rgDig;
	}

	public String getRgNum() {
		return this.rgNum;
	}

	public void setRgNum(String rgNum) {
		this.rgNum = rgNum;
	}

	public Usuario getUsuarioAtiva() {
		return this.usuarioAtiva;
	}

	public void setUsuarioAtiva(Usuario usuarioAtiva) {
		this.usuarioAtiva = usuarioAtiva;
	}
	
	public Usuario getUsuarioAlt() {
		return this.usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}
	
	public Usuario getUsuarioDesativa() {
		return this.usuarioDesativa;
	}

	public void setUsuarioDesativa(Usuario usuarioDesativa) {
		this.usuarioDesativa = usuarioDesativa;
	}
	
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ValorTabAux getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(ValorTabAux tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getIdTipoPessoa() {
		if(idTipoPessoa==null&&tipoPessoa!=null)idTipoPessoa=tipoPessoa.getIdValTabAux();
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(Long idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}
	
}