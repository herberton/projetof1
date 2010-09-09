package qcs.base.seguranca.web.mb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.base.seguranca.view.list.StatusUsuarioEnum;
import qcs.base.seguranca.web.dataprov.UsuarioDataProvider;
import qcs.datamodel.BaseMB;

public class PrivilegioUsuarioMB extends BaseMB {
	protected static Log log = LogFactory.getLog(PrivilegioUsuarioMB.class);
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private UsuarioDataProvider usuarioDataProvider;
	private UsuarioView view;
	private StatusUsuarioEnum statusUsuarioEnum;
	
	public UsuarioDataProvider getUsuarioDataProvider() {
		return usuarioDataProvider;
	}

	public StatusUsuarioEnum getStatusUsuarioEnum() {
		return statusUsuarioEnum;
	}

	public void setStatusUsuarioEnum(StatusUsuarioEnum statusUsuarioEnum) {
		this.statusUsuarioEnum = statusUsuarioEnum;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	protected void clear() {
		this.usuario = null;
		if(getCurrentState() == null || getCurrentState().equals(PESQUISAR_STATE)){
			this.usuario = new Usuario();
		}else{
			this.usuario = null;
		}
	}

	@Override
	public void salvar() {

	}
	public void efetuarPesquisa(){
		setCurrentState(PESQUISAR_STATE);
	}

	public String getTextoDocumento(){
		return usuario.getLogin();
	}

	public UsuarioView getView() {
		return view;
	}

	public void setView(UsuarioView view) {
		this.view = view;
	}

	@Override
	public void adicionar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir() {
		// TODO Auto-generated method stub

	}

	public void setUsuarioDataProvider(UsuarioDataProvider usuarioDataProvider) {
		this.usuarioDataProvider = usuarioDataProvider;
	}
}