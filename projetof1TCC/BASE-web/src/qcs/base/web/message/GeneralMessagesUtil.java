package qcs.base.web.message;

import java.lang.annotation.Annotation;
import java.util.ResourceBundle;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import qcs.base.constantes.ErrosOracle;
import qcs.webproject.aplicacao.mb.Mensagem;

public class GeneralMessagesUtil {

	protected static final String PROPERTIES_FILE_NAME = "appConf";
	protected static final String TITULO_ERRO = "titulo.erro";
	protected static final String TITULO_SUCESSO_INCLUIR = "titulo.sucesso.incluir";
	protected static final String TITULO_SUCESSO_ALTERAR = "titulo.sucesso.alterar";
	protected static final String TITULO_SUCESSO_EXCLUIR = "titulo.sucesso.excluir";

	protected static final String INCLUIR_SUCESSO = "msg.incluir.sucesso";
	protected static final String ALTERAR_SUCESSO = "msg.alterar.sucesso";
	protected static final String EXCLUIR_SUCESSO = "msg.excluir.sucesso";

	protected static final String INCLUIR_ERRO = "msg.erro.incluir"; 
	protected static final String ALTERAR_ERRO = "msg.erro.alterar";
	protected static final String EXCLUIR_ERRO = "msg.erro.excluir";
	
	protected static final String EXCLUIR_ERRO_DEPENDENTE = "msg.excluir.erro.dependente";
	protected static final String EXCLUIR_ERRO_UNIQUE_CONSTRAINT = "msg.excluir.erro.unique.constraint";
	protected static final String INCLUIR_ERRO_CONSTRAINT_VIOLATION = "msg.incluir.erro.constraint.violation";
			
	protected static final String ERRO_INVALID_STATE = "msg.erro.invalid.state";
	
	protected static ResourceBundle appConf;

	public static Mensagem criarMensagemErroApartirDeEJB(EJBException e, Object persistenceClass){
		Throwable t = e.getCause();
		StringBuffer erro = new StringBuffer();
		String tituloErro = appConf.getString(TITULO_ERRO);
		if(t instanceof ConstraintViolationException){
			ConstraintViolationException ex = (ConstraintViolationException)e.getCausedByException();
			if(ex.getConstraintName() != null && ex.getConstraintName().trim().endsWith("UK")){
				Annotation[] anotacoes = persistenceClass.getClass().getDeclaredAnnotations();
				for(Annotation a :anotacoes){
					if(a instanceof Table){
						UniqueConstraint[] uks = ((Table) a).uniqueConstraints();
						for(UniqueConstraint uk : uks){
							String[] colunas = uk.columnNames();
							erro.append(colunas.length > 1 ? " Os campos " : " O campo ");

							for(String coluna: colunas){
								erro.append(coluna+", ");
							}
							erro.deleteCharAt(erro.length()-2);
							erro.append(colunas.length > 1 ? " devem ser únicos." : " deve ser único.");
						}
					}
				}

				String mensagem = appConf.getString(INCLUIR_ERRO_CONSTRAINT_VIOLATION);
				mensagem.replace("{0}", ex.getConstraintName());
				mensagem.replace("{1}", erro.toString());
				return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
			}else{
				String mensagem = appConf.getString(INCLUIR_ERRO_CONSTRAINT_VIOLATION);
				mensagem.replace("{0}", ex.getMessage());
				mensagem.replace("{1}", "");
				return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
			}
		}else{
			String mensagem = appConf.getString(INCLUIR_ERRO);
			mensagem.replace("{0}", e.getMessage());
			return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
		}
	}

	public static Mensagem criarMensagemSucessoInclusaoApartirDe(String descricaoObjeto){
		String mensagem = appConf.getString(INCLUIR_SUCESSO);
		String titulo = appConf.getString(TITULO_SUCESSO_INCLUIR);
		mensagem = mensagem.replace("{0}", descricaoObjeto);
		return new Mensagem(Mensagem.PATH_CORRECT_ICO, titulo, mensagem);
	}

	public static Mensagem criarMensagemSucessoAlteracaoApartirDe(String descricaoObjeto){
		String mensagem = appConf.getString(ALTERAR_SUCESSO);
		String titulo = appConf.getString(TITULO_SUCESSO_ALTERAR);
		mensagem = mensagem.replace("{0}", descricaoObjeto);
		return new Mensagem(Mensagem.PATH_CORRECT_ICO, titulo, mensagem);
	}

	public static Mensagem criarMensagemSucessoExclusaoApartirDe(String descricaoObjeto){
		String mensagem = appConf.getString(EXCLUIR_SUCESSO);
		String titulo = appConf.getString(TITULO_SUCESSO_EXCLUIR); 
		mensagem = mensagem.replace("{0}", descricaoObjeto);
		return new Mensagem(Mensagem.PATH_CORRECT_ICO, titulo, mensagem);
	}

	public static Mensagem criarMensagemErroApartirDe(Exception e, String descricaoObjeto){
		String tituloErro = appConf.getString(TITULO_ERRO);
		e.printStackTrace();
		if(e instanceof ConstraintViolationException){
			ConstraintViolationException ex = (ConstraintViolationException)e;
			
			System.out.println("ERRO: "+ex.getErrorCode()+", ex.getConstraintName() "+ex.getConstraintName());
			if(ex.getErrorCode() == ErrosOracle.CHILD_RECORD_FOUND){
				String mensagem = appConf.getString(EXCLUIR_ERRO_DEPENDENTE);
				mensagem = mensagem.replace("{0}", descricaoObjeto);
				return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
			}else if(ex.getErrorCode() == ErrosOracle.UNIQUE_CONSTRAINT){
				String mensagem = appConf.getString(EXCLUIR_ERRO_UNIQUE_CONSTRAINT);
				mensagem = mensagem.replace("{0}", descricaoObjeto);
				mensagem = mensagem.replace("{1}", ex.getConstraintName());
				mensagem = mensagem.replace("{2}", ex.getMessage());
				return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
			}
			
		}else if(e instanceof InvalidStateException){
			InvalidStateException ex = (InvalidStateException)e;
			String mensagem = appConf.getString(ERRO_INVALID_STATE);
			mensagem.replace("{0}", descricaoObjeto);
			StringBuilder strBuilder = new StringBuilder(mensagem+"\n\r");
			for (InvalidValue value : ex.getInvalidValues()) {
				strBuilder.append("Campo: "+value.getPropertyName()+", erro: "+value.getMessage());
			}
			mensagem.replace("{1}", strBuilder.toString());
			return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
		}
		String mensagem = appConf.getString(ALTERAR_ERRO);
		mensagem.replace("{0}", descricaoObjeto);
		mensagem.replace("{1}", e.toString());
		return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
	}

	public static Mensagem criarMensagemErroApartirDe(String mensagem, String descricaoObjeto){
		String tituloErro = appConf.getString(TITULO_ERRO);
		String mensagemBundle = appConf.getString(EXCLUIR_ERRO);
		mensagemBundle.replace("{0}", descricaoObjeto);
		mensagemBundle.replace("{1}", mensagem);
		return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagemBundle);
	}

	public static Mensagem criarMensagemErroGenerica(String mensagem){
		String tituloErro = appConf.getString(TITULO_ERRO);
		return new Mensagem(Mensagem.PATH_ERROR_ICO, true, tituloErro, true, mensagem);
	}
	
	public static void incluirMensagem(String nomeComponente, String mensagem, Exception e){
		FacesContext.getCurrentInstance().addMessage(nomeComponente, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, e.getMessage()));
	}
	static{
		if(appConf == null){
			appConf = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
		}
	}
}
