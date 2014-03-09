package br.com.scrumming.core.manager.implementations;

import java.util.List;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

public class ItemBacklogManager extends AbstractManager<ItemBacklog, Integer> implements
		IItemBacklogManager{

	@Override
	public AbstractRepositorio<ItemBacklog, Integer> getRepositorio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvarItemBacklog(ItemBacklog itemBacklog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
