package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;

@Repository
public class ItemBacklogRepositorio extends AbstractRepositorio<ItemBacklog, Integer> {
	
	/**
     * @author Naftali consultar item de bakclog por Projeto
     * @param projetoID List<ItemBacklog> do ItemBacklog
     * @return uma coleção de Itens de Backlog
     * */
    @SuppressWarnings("unchecked")
    public List<ItemBacklog> consultarPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("projeto", "projeto");
        criteria.add(Restrictions.eq("projeto.codigo", projetoID));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }
    
    /**
     * Consultar a lista de Itens que pertecem à SprintBacklog.
     * @param sprint
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<ItemBacklog> consultarPorSprintBacklog(Integer sprintID) {
    	Criteria criteria = createCriteria();
        criteria.createAlias("sprintBacklog", "sprintBacklog");
        criteria.add(Restrictions.eq("sprintBacklog.sprint.codigo", sprintID));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }
}
