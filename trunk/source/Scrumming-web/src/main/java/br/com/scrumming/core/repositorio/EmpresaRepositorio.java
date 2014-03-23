package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Empresa;

@Repository
public class EmpresaRepositorio extends AbstractRepositorio<Empresa, Integer> {

	/**
	 * Consultar Empresas pelo nome
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	@SuppressWarnings("unchecked")
	public List<Empresa> consultarPorNome(String nome){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("nome"));
		return Collections.checkedList(criteria.list(), Empresa.class);
	}
	
	/**
	 * Consultar Empresas pelo código
	 * @param Código da Empresa
	 * @return Uma lista de Empresas
	 */
	@SuppressWarnings("unchecked")
	public List<Empresa> consultarPorCodigo(Integer EmpresaID){
		Criteria criteria = createCriteria();
        criteria.createAlias("empresa", "empresa");
        criteria.add(Restrictions.eq("empresa.codigo", EmpresaID));
        return Collections.checkedList(criteria.list(), Empresa.class);
	}
}