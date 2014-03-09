package br.com.scrumming.core.repositorio;

import org.hibernate.Criteria;

import java.util.List;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;

public class ItemBacklogRepositorio extends
		AbstractRepositorio<Entidade, Chave> {
	/**
     * @author Naftali consultar item de bakclog por nome
     * @param nome Nome do ItemBacklog
     * @return uma coleção de Itens de Backlog
     * */
    @SuppressWarnings("unchecked")
    public List consultarPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("projeto", "projeto");
        criteria.add(Restrictions.eq("projeto", projetoID));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }

}
