package qcs.base.enderecamento;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.ValorTabAux;


/**
 * The persistent class for the LOGRADOURO database table.
 * 
 */
@Entity
public class Logradouro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="LogradouroSequence",sequenceName="LOGRADOURO_SEQ", allocationSize=5)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LogradouroSequence")
	@Column(name="ID_LOGRADOURO",nullable=false,columnDefinition="NUMBER(9,0)")
	private Long idLogradouro;
	
	@Length(max=25,min=0)
	@Column(columnDefinition="VARCHAR2(25) NULL")
	private String abrev;
	
	@NotNull
	@Length(max=1,min=1)
	@Column(nullable=false,columnDefinition="CHAR(1)")
	private String ativo;
	
	@Transient
	private Boolean ativoBoolean;
	
	@NotNull
	@Length(max=8,min=1)
	@Column(nullable=false,columnDefinition="VARCHAR2(8)")
	private String cep;

    @Temporal(TemporalType.DATE)
	@Column(name="DATA_ATUALIZACAO",columnDefinition="DATE NULL")
	private Date dataAtualizacao;

    @Length(max=50,min=1)
    @Column(nullable=false,columnDefinition="VARCHAR2(50)")
    private String logradouro;

	//bi-directional many-to-one association to Bairro
    @ForeignKey(name="BAIRRO_CEP_FK")
    @ManyToOne
	@JoinColumn(name="ID_BAIRRO",columnDefinition="NUMBER(5,0) NULL")
	private Bairro bairro;

	//uni-directional many-to-one association to ValoresTabAux
    @NotNull
    @ForeignKey(name="VAL_TAB_AUX_CEP_TIPO_LOG_FK")
    @ManyToOne
	@JoinColumn(name="ID_TIPO_LOG",nullable=false,columnDefinition="NUMBER(9,0)")
	private ValorTabAux tipoLog;
    
    @Transient
    private Long idTipoLog;
    
    @ForeignKey(name="TAB_AUX_LOGRADOURO_FK")
    @ManyToOne
    @JoinColumn(name="ID_TAB_AUX_TIPO_LOG",columnDefinition="NUMBER(3,0) NULL")
    private TabelaAuxiliar tabelaAuxiliar;
    
    @ForeignKey(name="CIDADE_LOGRADOURO_FK")
    @ManyToOne
    @JoinColumn(name="ID_CIDADE",columnDefinition="NUMBER(5,0) NULL")
    private Cidade cidade;

    public Logradouro() {
    }

	public Long getIdLogradouro() {
		return this.idLogradouro;
	}

	public void setIdLogradouro(Long idLogradouro) {
		this.idLogradouro = idLogradouro;
	}
	
	public String getAbrev() {
		return this.abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

	public String getAtivo() {
		return this.ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public ValorTabAux getTipoLog() {
		return this.tipoLog;
	}

	public void setTipoLog(ValorTabAux tipoLog) {
		this.tipoLog = tipoLog;
	}

	public Long getIdTipoLog() {
		if(idTipoLog==null && tipoLog!=null) idTipoLog = tipoLog.getIdValTabAux();
		return idTipoLog;
	}

	public void setIdTipoLog(Long idTipoLog) {
		this.idTipoLog = idTipoLog;
	}

	public Boolean getAtivoBoolean() {
		if(ativoBoolean==null){
			ativo="S";
			return true;
		}
		if(ativo!=null && ativo.equals("S"))ativoBoolean = true;
		else ativoBoolean = false;
		return ativoBoolean;
	}

	public void setAtivoBoolean(Boolean ativoBoolean) {
		this.ativoBoolean = ativoBoolean;
		ativo = (ativoBoolean)?"S":"N";
	}

	public TabelaAuxiliar getTabelaAuxiliar() {
		return tabelaAuxiliar;
	}

	public void setTabelaAuxiliar(TabelaAuxiliar tabelaAuxiliar) {
		this.tabelaAuxiliar = tabelaAuxiliar;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
}