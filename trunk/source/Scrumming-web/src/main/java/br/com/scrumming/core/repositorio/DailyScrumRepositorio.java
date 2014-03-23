package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.DailyScrum;

@Repository
public class DailyScrumRepositorio  extends AbstractRepositorio<DailyScrum, Integer> {

	@SuppressWarnings("unchecked")
	public List<DailyScrum> consultarListaPorSprint(Integer sprintID) {
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprintID));
        return Collections.checkedList(criteria.list(), DailyScrum.class);
	}
}