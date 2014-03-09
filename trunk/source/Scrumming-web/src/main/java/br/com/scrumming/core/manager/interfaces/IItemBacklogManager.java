package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;

public interface IItemBacklogManager extends IManager<ItemBacklog, Integer>{

	void salvarItemBacklog(ItemBacklog itemBacklog);
	List<Sprint> consultarPorProjeto(Integer projetoID);
}
