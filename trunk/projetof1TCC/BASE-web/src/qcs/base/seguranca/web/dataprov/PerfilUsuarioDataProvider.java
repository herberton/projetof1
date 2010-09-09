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
				+ " não encontrado.");
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
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
			setSize(getPerfilUsuarioDao().getMaxRows(atributosFiltros, valores));
		} catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro PerfilUsuarioDataProvider : getRowCount", e.getMessage()));
		}
		return getSize();
	}

	public PerfilUsuarioDao getPerfilUsuarioDao() {
		log.debug("verificação da sessão: "+session);
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
