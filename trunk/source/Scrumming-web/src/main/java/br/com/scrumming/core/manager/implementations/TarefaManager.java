package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.repositorio.TarefaRepositorio;
import br.com.scrumming.domain.Tarefa;

public class TarefaManager extends AbstractManager<Tarefa, Integer> implements ITarefaManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private TarefaRepositorio tarefaRepositorio;

	@Override
	public AbstractRepositorio<Tarefa, Integer> getRepositorio() {
		return this.tarefaRepositorio;
	}

	@Override
	public void salvarTarefa(Tarefa tarefa) {
		insertOrUpdate(tarefa);
	}

	@Override
	public void removerTarefa(Tarefa tarefa) {
		remove(tarefa);
		// TODO falta criar validacoes para remover entidades que ainda nao existem
		// as entidades são: ReporteTarefa e TarefaFaforita

	}

	@Override
	public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID) {
		return this.tarefaRepositorio.consultarPorItemBacklog(itemBacklogID);
	}

}
