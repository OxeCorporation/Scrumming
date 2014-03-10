package br.com.scrumming.core.manager.interfaces;


import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;

public interface IItemBacklogManager extends IManager<ItemBacklog, Integer> {
	
	void salvarItemBlacklog(ItemBacklog item);
	void cancelarItem(ItemBacklog itemBacklog);
	List<ItemBacklog> consultarPorProjeto(Integer projetoID);
}