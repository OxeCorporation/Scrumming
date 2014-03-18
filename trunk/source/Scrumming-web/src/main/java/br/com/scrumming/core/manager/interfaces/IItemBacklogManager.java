package br.com.scrumming.core.manager.interfaces;


import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;

public interface IItemBacklogManager extends IManager<ItemBacklog, Integer> {
	
	void salvarItemBlacklog(ItemBacklog itemBacklog);
	void cancelarItem(ItemBacklog item);
	List<ItemBacklog> consultarPorProjeto(Integer projetoID);
	ItemBacklog consultarItemPorID(Integer itemID);
}