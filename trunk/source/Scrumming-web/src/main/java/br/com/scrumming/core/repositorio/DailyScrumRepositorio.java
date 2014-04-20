package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.DailyScrum;

@Repository
public class DailyScrumRepositorio  extends AbstractRepositorio<DailyScrum, Integer> {

	@SuppressWarnings("unchecked")
	public List<DailyScrum> listarDailyScrumPorSprint(Integer sprintID) {
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprintID));
        criteria.addOrder(Order.asc("dataHora"));
        return Collections.checkedList(criteria.list(), DailyScrum.class);
	}
	
	public DailyScrum consultarProximoDailyScrum(Integer DailyScrumID) {
		Criteria criteria = createCriteria();
        //criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("codigo", DailyScrumID));
        return (DailyScrum) criteria.uniqueResult();
	}
}