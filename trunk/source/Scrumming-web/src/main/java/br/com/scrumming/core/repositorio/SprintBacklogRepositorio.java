package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintBacklogChave;

@Repository
public class SprintBacklogRepositorio extends AbstractRepositorio<SprintBacklog, SprintBacklogChave> {

	/**
	 * Efetua uma consulta de um item da SprintBacklog pela combinação de sua chave composta.
	 * @param sprint
	 * @param itemBacklog
	 * @return
	 */
	public SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprint.getChave()));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklog.getChave()));
        return (SprintBacklog) criteria.uniqueResult();
	}
	
	/**
	 * Efetua uma consulta de um item da SprintBacklog pela combinação de sua chave composta.
	 * @param sprint
	 * @param itemBacklog
	 * @return
	 */
	public SprintBacklog consultaAtivosPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprint.getChave()));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklog.getChave()));
        criteria.add(Restrictions.eq("isAtivo", true));
        return (SprintBacklog) criteria.uniqueResult();
	}
	
	/**
	 * Consulta os Itens Backlog que estão na sprint.	
	 * @param sprintID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemBacklog> consultarItensAtivosSprintBacklogPorSprint(Integer sprintID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprintID));
        criteria.add(Restrictions.eq("isAtivo", true));
        criteria.setProjection(Projections.property("itemBacklog"));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<ItemBacklog> consultarItensSprintBacklogPorSprint(Integer sprintID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprintID));
        return Collections.checkedList(criteria.list(), SprintBacklog.class);
	}*/
}