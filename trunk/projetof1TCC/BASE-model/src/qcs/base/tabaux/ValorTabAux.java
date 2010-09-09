package qcs.base.tabaux;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.validator.NotNull;


/**
 * The persistent class for the VALORES_TAB_AUX database table.
 * 
 */
@Entity
@Table(name="VALOR_TAB_AUX")
public class ValorTabAux implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ValorTabAuxSequence", sequenceName="VALOR_TAB_AUX_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ValorTabAuxSequence")
	@Column(name="ID_VAL_TAB_AUX")
	private Long idValTabAux;

	private String abrev;

	private String nome;

	private String observacao;

	//bi-directional many-to-one association to TabelaAuxiliar
    @NotNull
	@ManyToOne
	@JoinColumn(name="ID_TAB_AUX", columnDefinition="NUMBER(3,0)")
	private TabelaAuxiliar tabelaAuxiliar;

    public ValorTabAux() {
    }

	public Long getIdValTabAux() {
		return this.idValTabAux;
	}

	public void setIdValTabAux(Long idValTabAux) {
		this.idValTabAux = idValTabAux;
	}

	public String getAbrev() {
		return this.abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TabelaAuxiliar getTabelaAuxiliar() {
		return this.tabelaAuxiliar;
	}

	public void setTabelaAuxiliar(TabelaAuxiliar tabelaAuxiliar) {
		this.tabelaAuxiliar = tabelaAuxiliar;
	}
	
}