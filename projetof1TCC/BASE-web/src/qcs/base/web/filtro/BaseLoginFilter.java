package qcs.base.web.filtro;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.web.dataprov.UsuarioDataProvider;
import qcs.base.web.aplicacao.mb.StatusAplicacao;
import qcs.webproject.login.filtro.LoginFilter;

public class BaseLoginFilter extends LoginFilter {
	protected static Log log = LogFactory.getLog(BaseLoginFilter.class);
	private StatusAplicacao statusAplicacao;

	@Override
	public void carregarStatusAplicacao(){
		log.debug("Carregando status da aplicação");
		String usuario = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		log.debug("Usuário logado: "+usuario);
		try{
			statusAplicacao.setHoraInicioSessao(new Date());
			log.debug("Carregando usuário na sessão");
//			ServidorDataProvider servidorDataProv = (ServidorDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "servidorDataProvider");
			UsuarioDataProvider usuarioDataProv = (UsuarioDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "usuarioDataProvider");

			Usuario usuarioLogado = usuarioDataProv.consultar(usuario);
			log.debug("\tusuário: "+usuarioLogado.getLogin());
//			Servidor servidorLogado = servidorDataProv.consultar(usuarioLogado.getIdServidor());
//			log.debug("\tservidor: "+servidorLogado.getNome());
			
//			statusAplicacao.setServidorLogado(servidorLogado);
			statusAplicacao.setUsuarioLogado(usuarioLogado);
		} catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao carregar status da aplicação", ex.getMessage()));
		}
	}

	@Override
	public boolean isStatusAplicacaoCarregado(){
		statusAplicacao = (StatusAplicacao)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(),null,"statusAplicacao");
		if(statusAplicacao == null) return false;
		return statusAplicacao.getHoraInicioSessao() != null;
	}
}
