package br.com.scrumming.core.repositorio;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;

@Repository
public class SprintBacklogRepositorio extends AbstractRepositorio<SprintBacklog, Integer> {

	public SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		
		Criteria criteria = createCriteria();
        criteria.createAlias("sprint", "sprint");
        criteria.add(Restrictions.eq("sprint.codigo", sprint.getChave()));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.codigo", itemBacklog.getChave()));
        return (SprintBacklog) criteria.uniqueResult();
	}
}