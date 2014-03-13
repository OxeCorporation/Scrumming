package br.com.scrumming.core.manager.interfaces;


import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

public interface IItemBacklogManager extends IManager<ItemBacklog, Integer> {
	
	void salvarItemBlacklog(ItemBacklog itemBacklog);
	void cancelarItem(ItemBacklog item);
	List<ItemBacklog> consultarPorProjeto(Integer projetoID);
	List<ItemBacklog> consultarPorSprintBacklog(Sprint sprint);
}