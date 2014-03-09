package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

public interface ISprintManager extends IManager<Sprint, Integer> {

	void salvarSprint(Sprint sprint, List<ItemBacklog> itensBacklogSprint, List<ItemBacklog> itensBacklogProduto);
    List<Sprint> consultarPorProjeto(Integer projetoID);
    void fecharSprint(Sprint sprint);
}