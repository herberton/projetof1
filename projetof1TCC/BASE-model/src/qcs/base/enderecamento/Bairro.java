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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.Digits;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the BAIRRO database table.
 * 
 */
@Entity
public class Bairro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BairroSequence", sequenceName="BAIRRO_SEQ", allocationSize=2)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BairroSequence")
	@Digits(integerDigits=5,fractionalDigits=0)
	@Column(name="ID_BAIRRO",nullable=false,columnDefinition="NUMBER(5,0)")
	private Long idBairro;

	
	@Column(columnDefinition="VARCHAR(25)")
	private String abrev;
	
	@NotNull
	@Length(max=50, min=1)
	@Column(columnDefinition="VARCHAR(50)",nullable=false)
	private String nome;

	//bi-directional many-to-one association to Cidade
	@ForeignKey(name="CIDADE_BAIRRO_FK")
	@NotNull
    @ManyToOne
	@JoinColumn(name="ID_CIDADE",nullable=false,columnDefinition="NUMBER(5,0)")
	private Cidade cidade;

	//bi-directional many-to-one association to Logradouro
	@OneToMany(mappedBy="bairro")
	private List<Logradouro> logradouros;

    public Bairro() {
    }

	public Long getIdBairro() {
		return this.idBairro;
	}

	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
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

	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Logradouro> getLogradouros() {
		return this.logradouros;
	}

	public void setLogradouros(List<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}
	
}