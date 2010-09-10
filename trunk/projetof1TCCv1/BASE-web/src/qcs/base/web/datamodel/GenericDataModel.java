package qcs.base.web.datamodel;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.web.aplicacao.mb.StatusAplicacao;

public abstract class GenericDataModel<T, K, TIPOPK extends Serializable> extends
qcs.datamodel.GenericDataModel<T, K, TIPOPK> {
	protected static Log log = LogFactory.getLog(GenericDataModel.class);
	private StatusAplicacao statusAplicacao;
	private static final long serialVersionUID = 1L;

	@Override
	public void carregarUsuarioNaSessao(){
		if(!isStatusAplicacaoCarregado()){
			log.debug("Carregando status da aplica��o");
			String usuario = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			log.debug("Usu�rio logado: "+usuario);
			// TODO: N�o fazer nenhuma altera��o
//			try{
//				statusAplicacao.setHoraInicioSessao(new Date());
//				log.debug("Carregando usu�rio na sess�o");
//				ServidorDataProvider servidorDataProv = (ServidorDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "servidorDataProvider");
//				UsuarioDataProvider usuarioDataProv = (UsuarioDataProvider)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "usuarioDataProvider");
//
//				Usuario usuarioLogado = usuarioDataProv.consultar(usuario);
//				log.debug("\tusu�rio: "+usuarioLogado.getLogin());
//				Servidor servidorLogado = servidorDataProv.consultar(usuarioLogado.getIdServidor());
//				log.debug("\tservidor: "+servidorLogado.getNome());
//
//				statusAplicacao.setServidorLogado(servidorLogado);
//				statusAplicacao.setUsuarioLogado(usuarioLogado);
//			} catch(Exception ex){
//				ex.printStackTrace();
//				FacesContext.getCurrentInstance().addMessage(null, 
//						new FacesMessage("Erro ao carregar status da aplica��o", ex.getMessage()));
//			}
		}
	}

	public boolean isStatusAplicacaoCarregado(){
		statusAplicacao = (StatusAplicacao)getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(),null,"statusAplicacao");
		if(statusAplicacao == null) return false;
		return statusAplicacao.getHoraInicioSessao() != null;
	}

}
