package qcs.base.web.aplicacao.mb;

import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginMB {
	protected static Log log = LogFactory.getLog(LoginMB.class);
	private String usuario;
	private String senha;

	public void carregarUsuario(PhaseEvent e){
		try{
			if(usuario != null && senha != null){
				// TODO : Implementar métodos de carga de usuário
//				ServidorDataProvider servidorDataProv = (ServidorDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "servidorDataProvider");
//				UsuarioDataProvider usuarioDataProv = (UsuarioDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "usuarioDataProvider");
//
//				Usuario usuario = usuarioDataProv.consultar(this.usuario);
//				getELResolver().setValue(FacesContext.getCurrentInstance().getELContext(),null,"usuarioLogado", usuario);
//				log.debug("usuário: "+usuario.getLogin());
//				Servidor servidor = servidorDataProv.consultar(usuario.getIdServidor());
//				getELResolver().setValue(FacesContext.getCurrentInstance().getELContext(),null,"servidorLogado", servidor);
//				log.debug("servidor: "+servidor.getNome());
			}
		} catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar Usuário", ex.getMessage()));
		}	
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
		getELResolver().setValue(FacesContext.getCurrentInstance().getELContext(),null,"usuario", usuario);
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
		getELResolver().setValue(FacesContext.getCurrentInstance().getELContext(),null,"senha", usuario);
	}
	
	public ELResolver getELResolver(){
		return FacesContext.getCurrentInstance().getApplication().getELResolver();
	}

}
