package qcs.persistence.rhdefensoria.view;

import java.io.Serializable;
import java.util.Date;

public class LovDispositivoView implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idDispositivo;
	private String statusDispositivo;
	private Date dataCadastro;
	private String ip;
	
	public Long getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public String getStatusDispositivo() {
		return statusDispositivo;
	}
	public void setStatusDispositivo(String statusDispositivo) {
		this.statusDispositivo = statusDispositivo;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}