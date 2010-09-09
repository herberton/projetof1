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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade utilizando os filtros.
	 * @param atributosFiltros - são os filtros que forem preenchidos pelo usuário na tela de pesquisa:
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é a expressão de comparação
	 * @param valores são os valores que deverão ser comparados
	 * <br>o primeiro parâmetro do map é uma String representando o nome do atributo que deve ser filtrado da entidade
	 * <br>o segundo atributo é o valor que deve ser comparado
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
		log.debug("verificação da sessão: "+session);
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
