package qcs.base.web.aplicacao.mb;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import qcs.webproject.aplicacao.mb.Mensagem;

public class Autenticacao {
	private Mensagem msg;
	
	public Mensagem getMsg() {
		return msg;
	}

	public void setMsg(Mensagem msg) {
		this.msg = msg;
	}

	@SuppressWarnings("unused")
	public void logout(ActionEvent event){
//		log.debug("método logout de autenticação");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);
//		session.invalidate();
	}

}
