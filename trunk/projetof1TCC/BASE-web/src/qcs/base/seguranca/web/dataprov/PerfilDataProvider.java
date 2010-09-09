package qcs.base.seguranca.web.dataprov;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import qcs.base.seguranca.Perfil;
import qcs.base.seguranca.persistence.dao.PerfilDao;
import qcs.base.seguranca.persistence.dao.impl.PerfilDaoImpl;
import qcs.base.seguranca.persistence.view.PerfilView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class PerfilDataProvider extends HibernateDataProvider<PerfilView, Perfil, Long> {
	protected static Log log = LogFactory.getLog(PerfilDataProvider.class);
	private static final long serialVersionUID = 1L;
	private PerfilDao perfilDao;

	@Override
	public PerfilView getObjectByPk(Object idPk) {
		for (PerfilView documento : getAllItems()) {
			if (documento.getIdPerfil().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Perfil pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (PerfilView perfil : getAllItems()) {
			if (perfil.getIdPerfil().equals(idPk)) {
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
	public List<PerfilView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) {
		List<PerfilView> lista = null;
		try{
			lista = (List<PerfilView>)getPerfilDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

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
					new FacesMessage("Erro PerfilDataProvider : getObjectByRange", e.getMessage()));
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
			setSize(getPerfilDao().getMaxRows());
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilDataProvider : getRowCount", e.getMessage()));
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
			setSize(getPerfilDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public PerfilDao getPerfilDao() {
		log.debug("verifica��o da sess�o: "+session);
		this.perfilDao = new PerfilDaoImpl(session);
		return perfilDao;
	}

	public void setPerfilDao(PerfilDao perfilDao) {
		this.perfilDao = perfilDao;
	}

	public Perfil alterar(Perfil objeto) throws InfrastructureException, Exception{
		objeto = getPerfilDao().update(objeto);
		session.flush();

		return objeto;
	}

	public Perfil incluir(Perfil objeto) throws InfrastructureException, Exception{
		objeto = getPerfilDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(Perfil objeto) throws InfrastructureException, Exception{
		getPerfilDao().makeTransient(objeto);
		session.flush();
	}

	public Perfil consultar(Long idPerfil) throws InfrastructureException, Exception{
		return getPerfilDao().get(idPerfil);
	}

}
