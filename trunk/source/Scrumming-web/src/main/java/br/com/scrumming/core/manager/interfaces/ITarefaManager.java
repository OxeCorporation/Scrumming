package br.com.scrumming.core.manager.interfaces;

import java.util.List;
import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Tarefa;

public interface ITarefaManager extends IManager<Tarefa, Integer>{
	
	void salvar(Tarefa tarefa);
	void remover(Tarefa tarefa);
	
	List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID);
}
