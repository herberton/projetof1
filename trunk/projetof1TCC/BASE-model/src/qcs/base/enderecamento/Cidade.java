package qcs.base.enderecamento;

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

import qcs.base.tabaux.TabelaAuxiliar;
import qcs.base.tabaux.ValorTabAux;


/**
 * The persistent class for the CIDADE database table.
 * 
 */
@Entity
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CidadeSequence", sequenceName="CIDADE_SEQ", allocationSize=2)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CidadeSequence")
	@Digits(integerDigits=5,fractionalDigits=0)
	@Column(name="ID_CIDADE",nullable=false,columnDefinition="NUMBER(5,0)")
	private Long idCidade;

	@NotNull
	@Length(max=50,min=1)
	@Column(name="NOME_CIDADE",nullable=false,columnDefinition="VARCHAR2(50)")
	private String nomeCidade;
	
	@Length(max=50,min=0)
	@Column(columnDefinition="VARCHAR2(50)",nullable=true)
	private String abrev;

	//bi-directional many-to-one association to Bairro
	@OneToMany(mappedBy="cidade")
	private List<Bairro> bairros;

	//uni-directional many-to-one association to ValoresTabAux
    @ManyToOne
    @NotNull
    @ForeignKey(name="UF_CID_VAL_TAB_AUX_FK")
	@JoinColumn(name="ID_UF",nullable=false,columnDefinition="NUMBER(9,0)")
	private ValorTabAux uf;
    
    @ManyToOne
    @NotNull
    @ForeignKey(name="TAB_AUX_CIDADE_FK")
    @JoinColumn(name="ID_TAB_AUX_UF",nullable=false,columnDefinition="NUMBER(3,0)")
    private TabelaAuxiliar tabelaAuxiliar;
    
    @Transient
    private Long idValTabAux;

    public Cidade() {
    }

	public Long getIdCidade() {
		return this.idCidade;
	}

	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return this.nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public List<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}
	
	public ValorTabAux getUf() {
		return this.uf;
	}

	public void setUf(ValorTabAux uf) {
		this.uf = uf;
	}

	public Long getIdValTabAux() {
		if(idValTabAux==null && uf!=null)idValTabAux = uf.getIdValTabAux();
		return idValTabAux;
	}

	public void setIdValTabAux(Long idValTabAux) {
		this.idValTabAux = idValTabAux;
	}

	public String getAbrev() {
		return abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

	public TabelaAuxiliar getTabelaAuxiliar() {
		return tabelaAuxiliar;
	}

	public void setTabelaAuxiliar(TabelaAuxiliar tabelaAuxiliar) {
		this.tabelaAuxiliar = tabelaAuxiliar;
	}
	
}