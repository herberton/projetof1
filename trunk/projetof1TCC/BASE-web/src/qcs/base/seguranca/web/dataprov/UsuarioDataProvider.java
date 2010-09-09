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
				+ " não encontrado.");
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
	 * Método responsavel pelo retorno de uma range
	 * da objetos de acordo com a paginação da DataTable,
	 * principal método pela páginação sob demanda.
	 * 
	 * @param startID Range inicial
	 * @param numberOfRows Qtde de linhas
	 * @param sortField Campo para ordenação
	 * @param descending Modo de ordenação
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
	 * Método padrão para retorno da quantidade de linhas da tabela,
	 * método retorna quantidade sem nenhum filtro, para utilizar com filtro
	 * será necessario sobrecarregar o método.
	 * 
	 * @return int com quantidade de linhas da tabela.
	 */
	public int getRowCount() throws InfrastructureException, Exception{
		setSize(getUsuarioDao().getMaxRows());
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
