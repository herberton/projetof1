package qcs.base.web.aplicacao.mb;

import java.io.Serializable;
import java.util.Date;

import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.web.dataprov.UsuarioDataProvider;

public class IndexMB implements Serializable{
	protected static Log log = LogFactory.getLog(IndexMB.class);
	private StatusAplicacao statusAplicacao;
	private static final long serialVersionUID = 1L;

	public void carregarUsuarioNaSessao(PhaseEvent e){
		if(!isStatusAplicacaoCarregado()){
			log.debug("Carregando status da aplicação");
			String usuario = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			log.debug("Usuário logado: "+usuario);
			try{
				statusAplicacao.setHoraInicioSessao(new Date());
				log.debug("Carregando usuário na sessão");
				UsuarioDataProvider usuarioDataProv = (UsuarioDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "usuarioDataProvider");

				Usuario usuarioLogado = usuarioDataProv.consultar(usuario);
				log.debug("\tusuário: "+usuarioLogado.getLogin());

				statusAplicacao.setUsuarioLogado(usuarioLogado);
			} catch(Exception ex){
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage("Erro ao carregar status da aplicação", ex.getMessage()));
			}
		}
	}
	public boolean isStatusAplicacaoCarregado(){
		statusAplicacao = (StatusAplicacao)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(),null,"statusAplicacao");
		if(statusAplicacao == null) return false;
		return statusAplicacao.getHoraInicioSessao() != null;
	}

	public ELResolver getELResolver(){
		return FacesContext.getCurrentInstance().getApplication().getELResolver();
	}

}
