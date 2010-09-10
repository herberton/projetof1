package qcs.base.tabaux;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import java.util.List;


/**
 * The persistent class for the TABELA_AUXILIAR database table.
 * 
 */
@Entity
@Table(name="TABELA_AUXILIAR")
public class TabelaAuxiliar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TabelaAuxiliarSequence", sequenceName="VALOR_TAB_AUX_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TabelaAuxiliarSequence")
	@Column(name="ID_TAB_AUX",nullable=false,columnDefinition="NUMBER(3,0)")
	private Long idTabAux;
	
	@NotNull
	@Length(max=50,min=1)
	@Column(name="NOME_TAB_AUX",nullable=false,columnDefinition="VARCHAR2(50)")
	private String nomeTabAux;
	
	@Length(max=250,min=0)
	@Column(columnDefinition="VARCHAR2(250) NULL")
	private String descricao;

	//bi-directional many-to-one association to ValoresTabAux
	@OneToMany(mappedBy="tabelaAuxiliar")
	private List<ValorTabAux> valoresTabAux;

    public TabelaAuxiliar() {
    }

	public Long getIdTabAux() {
		return this.idTabAux;
	}

	public void setIdTabAux(Long idTabAux) {
		this.idTabAux = idTabAux;
	}

	public String getNomeTabAux() {
		return this.nomeTabAux;
	}

	public void setNomeTabAux(String nomeTabAux) {
		this.nomeTabAux = nomeTabAux;
	}

	public List<ValorTabAux> getValoresTabAux() {
		return this.valoresTabAux;
	}

	public void setValoresTabAux(List<ValorTabAux> valoresTabAux) {
		this.valoresTabAux = valoresTabAux;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}