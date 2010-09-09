package qcs.base.negocio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;


/**
 * The persistent class for the TERMINAL_CONSULTA database table.
 * 
 */
@Entity
@Table(name="TERMINAL_CONSULTA")
public class TerminalConsulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@SequenceGenerator(name="TERMINAL_CONSULTA_IDTERMINALCONSULTA_GENERATOR", sequenceName="SEQ_TERMINAL_CONSULTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TERMINAL_CONSULTA_IDTERMINALCONSULTA_GENERATOR")
	@Column(name="ID_TERMINAL_CONSULTA")
	private Long idTerminalConsulta;

	@Length(min=0,max=100)
	private String descricao;

	@Length(min=0,max=30)
	@Column(name="HOST_NAME")
	private String hostName;

	@Length(min=0,max=15)
	private String ip;

	@Length(min=0,max=150)
	private String localizacao;

    public TerminalConsulta() {
    }

	public Long getIdTerminalConsulta() {
		return this.idTerminalConsulta;
	}

	public void setIdTerminalConsulta(Long idTerminalConsulta) {
		this.idTerminalConsulta = idTerminalConsulta;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLocalizacao() {
		return this.localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

}