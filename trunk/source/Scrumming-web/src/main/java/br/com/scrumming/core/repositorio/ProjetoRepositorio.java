package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Projeto;

@Repository
public class ProjetoRepositorio extends AbstractRepositorio<Projeto, Integer> {

	/**
	 * Consulta Projeto pelo nome
	 * @param nome Nome da Sprint
	 * @return Uma colação de Sprints
	 */
	@SuppressWarnings("unchecked")
	public List<Projeto> consultarPorNome(String nome) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE).ignoreCase());
        criteria.addOrder(Order.asc("nome"));
        return Collections.checkedList(criteria.list(), Projeto.class);
    }
	
	/**
	 * Lista dos projetos da empresa.
	 * @param empresaID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Projeto> consultarPorEmpresa(Integer empresaID) {
		Criteria criteria = createCriteria();
        criteria.createAlias("empresa", "empresa");
        criteria.add(Restrictions.eq("empresa.codigo", empresaID));
        return Collections.checkedList(criteria.list(), Projeto.class);
    }
	
	/**
	 * Função que efetua a consulta da Sprint filtrando por um período específico.
	 * @param dataInicio Data de cadastro
	 * @param dataFim Data de cadastro
	 * @return Uma coleção de Sprints
	 */
	@SuppressWarnings("unchecked")
	public List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim) {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.between("data_cadastro", dataInicio, dataFim));
		criteria.addOrder(Order.asc("nome"));
		return Collections.checkedList(criteria.list(), Projeto.class);
	}

}
