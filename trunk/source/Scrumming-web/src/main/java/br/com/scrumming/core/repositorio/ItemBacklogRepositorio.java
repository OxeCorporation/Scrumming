package br.com.scrumming.core.repositorio;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.ItemBacklog;

@Repository
public class ItemBacklogRepositorio extends AbstractRepositorio<ItemBacklog, Integer> {
	
	/**
     * @author Naftali consultar item de bakclog por nome
     * @param nome Nome do ItemBacklog
     * @return uma coleção de Itens de Backlog
     * */
    @SuppressWarnings("unchecked")
    public List<ItemBacklog> consultarPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("projeto", "projeto");
        criteria.add(Restrictions.eq("projeto", projetoID));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }
}
