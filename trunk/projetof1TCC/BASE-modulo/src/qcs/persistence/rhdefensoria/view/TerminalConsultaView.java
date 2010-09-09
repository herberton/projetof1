package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;

public class TerminalConsultaView implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long idTerminalConsulta = null;
	private String ip = null;
	private String hostName = null;
	private String descricao = null;
	private String localizacao = null;
	
	
	//CONSTRUTOR...
	public TerminalConsultaView() { super(); }
	
	
	//GET...
	public Long getIdTerminalConsulta() {
		return idTerminalConsulta;
	}
	public String getIp() {
		return ip;
	}
	public String getHostName() {
		return hostName;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	
	
	//SET
	public void setIdTerminalConsulta(Long idTerminalConsulta) {
		this.idTerminalConsulta = idTerminalConsulta;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
}
