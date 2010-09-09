package qcs.base.seguranca.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.PerfilUsuario;
import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.dao.PerfilUsuarioDao;
import qcs.base.seguranca.persistence.dao.impl.PerfilUsuarioDaoImpl;
import qcs.base.seguranca.persistence.view.PerfilUsuarioView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class PerfilUsuarioDataProvider extends HibernateDataProvider<PerfilUsuarioView, Usuario, Long> {
	protected static Log log = LogFactory.getLog(PerfilUsuarioDataProvider.class);
	private static final long serialVersionUID = 1L;
	private PerfilUsuarioDao perfilUsuarioDao;

	@Override
	public PerfilUsuarioView getObjectByPk(Object idPk) {
		for (PerfilUsuarioView documento : getAllItems()) {
			if (documento.getIdPerfilUsuario().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("PerfilUsuario pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (PerfilUsuarioView perfil : getAllItems()) {
			if (perfil.getIdPerfilUsuario().equals(idPk)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * M�todo responsavel pelo retorno de uma range
	 * da objetos de acordo com a pagina��o da DataTable,
	 * principal m�todo pela p�gina��o sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordena��o
	 * @param descending Modo de ordena��o
	 * @return lista de objetos do perfil
	 */
	public List<PerfilUsuarioView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<PerfilUsuarioView> lista = null;
		try{
			lista = (List<PerfilUsuarioView>)getPerfilUsuarioDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

			if(lista!=null){
				while(lista.size() < numberOfRows){
					lista.add(classeObject.newInstance());
				}
			}
			setAllItems(lista);
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilUsuarioDataProvider : getObjectByRange", e.getMessage()));
		}
		return lista;
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * ser� necessario sobrecarregar o m�todo.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 */
	public int getRowCount() {
		try{
			setSize(getPerfilUsuarioDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilUsuarioDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade utilizando os filtros.
	 * @param atributosFiltros - s�o os filtros que forem preenchidos pelo usu�rio na tela de pesquisa:
	 * <br>o primeiro par�metro do map � uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo � a express�o de compara��o
	 * @param valores s�o os valores que dever�o ser comparados
	 * <br>o primeiro par�metro do map � uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo � o valor que deve ser comparado
	 * @return int com quantidade de linhas da tabela.
	 */	
	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object>valores) {
		try{
			setSize(getPerfilUsuarioDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilUsuarioDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public PerfilUsuarioDao getPerfilUsuarioDao() {
		log.debug("verifica��o da sess�o: "+session);
		this.perfilUsuarioDao = new PerfilUsuarioDaoImpl(session);
		return perfilUsuarioDao;
	}

	public void setPerfilUsuarioDao(PerfilUsuarioDao perfilUsuarioDao) {
		this.perfilUsuarioDao = perfilUsuarioDao;
	}

	public PerfilUsuario alterar(PerfilUsuario objeto) throws InfrastructureException, Exception{
		objeto = getPerfilUsuarioDao().update(objeto);
		session.flush();

		return objeto;
	}

	public PerfilUsuario incluir(PerfilUsuario objeto) throws InfrastructureException, Exception{
		objeto = getPerfilUsuarioDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(PerfilUsuario objeto) throws InfrastructureException, Exception{
		getPerfilUsuarioDao().makeTransient(objeto);
		session.flush();
	}

	public PerfilUsuario consultar(Long idUsuario) throws InfrastructureException, Exception{
		return getPerfilUsuarioDao().get(idUsuario);
	}

}
