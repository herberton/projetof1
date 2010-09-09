package qcs.base.enderecamento.persistence.dao;

import java.util.Collection;
import java.util.Map;

import qcs.base.enderecamento.Bairro;
import qcs.base.enderecamento.persistence.view.BairroView;
import qcs.persistence.template.hibernate.HibernateDao;

public interface BairroDao extends HibernateDao<Bairro, Long> {
	
	int getMaxRows(Map<String, Object> atributosFiltros);

	public Collection<BairroView> listWithFilterToView(
			Map<String, Object> atributosFiltros, int first, int max,
			String orderField, boolean descending);

	public Collection<BairroView> listWithFilterToView(
			Map<String, Object> atributosFiltros);
	
	
}
