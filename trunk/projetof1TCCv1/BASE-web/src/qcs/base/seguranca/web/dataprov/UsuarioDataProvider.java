package qcs.base.seguranca.web.dataprov;

import java.util.List;
import java.util.Map;

import qcs.base.seguranca.Usuario;
import qcs.base.seguranca.persistence.dao.UsuarioDao;
import qcs.base.seguranca.persistence.dao.impl.UsuarioDaoImpl;
import qcs.base.seguranca.persistence.view.UsuarioView;
import qcs.datamodel.HibernateDataProvider;
import qcs.persistence.hibernate.exception.InfrastructureException;

public class UsuarioDataProvider extends HibernateDataProvider<UsuarioView, Usuario, Long> {
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;

	@Override
	public UsuarioView getObjectByPk(Object idPk) {
		for (UsuarioView documento : getAllItems()) {
			if (documento.getIdUsuario().equals(idPk)) {
				return documento;
			}
		}
		throw new RuntimeException("Usuario pk=" + idPk.toString()
				+ " n�o encontrado.");
	}

	@Override
	public boolean hasObjectByPk(Object idPk) {
		for (UsuarioView usuario : getAllItems()) {
			if (usuario.getIdUsuario().equals(idPk)) {
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
	 * @return lista de objetos do usuario
	 */
	public List<UsuarioView> getObjectByRange(Map<String, Object> atributosFiltros, Integer startID, int numberOfRows, String sortField, boolean descending) throws InfrastructureException, Exception{
		List<UsuarioView> lista = null;
		lista = (List<UsuarioView>)getUsuarioDao().listWithFilterToView(atributosFiltros, startID, numberOfRows, sortField, descending);

		if(lista!=null){
			while(lista.size() < numberOfRows){
				lista.add(classeObject.newInstance());
			}
		}
		setAllItems(lista);
		return lista;
	}

	/**
	 * M�todo padr�o para retorno da quantidade de linhas da tabela,
	 * m�todo retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * ser� necessario sobrecarregar o m�todo.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 */
	public int getRowCount() throws InfrastructureException, Exception{
		setSize(getUsuarioDao().getMaxRows());
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
	public int getRowCount(Map<String, String> atributosFiltros, Map<String, Object>valores) throws InfrastructureException, Exception{
		setSize(getUsuarioDao().getMaxRows(atributosFiltros, valores));
		return getSize();
	}

	public int getRowCount(Map<String, Object> atributosFiltros) throws InfrastructureException, Exception{
		setSize(getUsuarioDao().getMaxRows(atributosFiltros));
		return getSize();
	}

	public UsuarioDao getUsuarioDao() {
		this.usuarioDao = new UsuarioDaoImpl(session);
		return usuarioDao;
	}

	public Usuario consultar(String usuario) throws InfrastructureException, Exception{
		return getUsuarioDao().buscar(usuario);
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public Usuario alterar(Usuario objeto)throws InfrastructureException, Exception{
		objeto = getUsuarioDao().update(objeto);
		session.flush();

		return objeto;
	}

	public Usuario incluir(Usuario objeto)throws InfrastructureException, Exception{
		objeto = getUsuarioDao().add(objeto);
		session.flush();
		return objeto;
	}

	public void excluir(Usuario objeto)throws InfrastructureException, Exception{
		getUsuarioDao().makeTransient(objeto);
		session.flush();
	}

	public Usuario consultar(Long id)throws InfrastructureException, Exception{
		return getUsuarioDao().get(id);
	}
}
