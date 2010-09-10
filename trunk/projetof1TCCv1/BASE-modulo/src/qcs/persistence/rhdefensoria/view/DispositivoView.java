package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;

public class DispositivoView implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long idDispositivo;
	private String ip;
	private String descricaoStatusDispositivo;
	
	//CONSTRUTOR...
	public DispositivoView() {
		// TODO Auto-generated constructor stub
	}
	
	//GET...
	public Long getIdDispositivo() {
		return idDispositivo;
	}
	public String getIp() {
		return ip;
	}
	public String getDescricaoStatusDispositivo() {
		return descricaoStatusDispositivo;
	}
	
	//SET
	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setDescricaoStatusDispositivo(String descricaoStatusDispositivo) {
		this.descricaoStatusDispositivo = descricaoStatusDispositivo;
	}
	
}
