package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Sprint;

@Repository
public class SprintRepositorio extends AbstractRepositorio<Sprint, Integer> {

	/**
	 * Consulta Sprints pelo nome
	 * @param nome Nome da Sprint
	 * @return Uma colação de Sprints
	 */
	@SuppressWarnings("unchecked")
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("projeto", "projeto");
        criteria.add(Restrictions.eq("projeto.codigo", projetoID));
        return Collections.checkedList(criteria.list(), Sprint.class);
    }
}