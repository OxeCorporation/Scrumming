package br.com.scrumming.core.repositorio;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
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
        criteria.add(Restrictions.eq("isAtivo", true));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }
    
    @SuppressWarnings("unchecked")
    public List<ItemBacklog> consultarSituacaoDoItemPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.createAlias("projeto", "projeto");
        criteria.add(Restrictions.eq("projeto.codigo", projetoID));
        criteria.add(Restrictions.eq("isAtivo", true));
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
    }
    
    /**
     * Consultar um item pelo se ID
     * @param Integer itemID
     * @return um objeto do tipo ItemBacklog
     */
    public ItemBacklog consultarItemPorID(Integer itemID) {
    	Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("codigo", itemID));
        return (ItemBacklog) criteria.uniqueResult();
    }
    
    /**
	 * Consulta os Itens Backlog ativos de um projeto.	
	 * @param sprintID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemBacklog> consultarItensAtivosSprintBacklogPorProjeto(Integer projetoID) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.property("itemBacklog"));
        criteria.createAlias("itemBacklog", "itemBacklog");
        criteria.add(Restrictions.eq("itemBacklog.projeto.codigo", projetoID));
        criteria.add(Restrictions.eq("itemBacklog.isAtivo", true));        
        return Collections.checkedList(criteria.list(), ItemBacklog.class);
	}
}