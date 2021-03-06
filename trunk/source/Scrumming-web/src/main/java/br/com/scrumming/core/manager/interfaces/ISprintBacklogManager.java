package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintBacklogChave;
import br.com.scrumming.domain.Tarefa;

public interface ISprintBacklogManager extends IManager<SprintBacklog, SprintBacklogChave> {

	SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog);
	void associarItemASprint(Sprint sprintPersistido, List<ItemBacklog> itensBacklogSprint);
	void desassociarItemASprint(Sprint sprintPersistido, List<ItemBacklog> itensBacklogProduto);
	List<ItemBacklog> consultarItensBacklogPorSprint(Integer sprintID);
	SprintBacklog consultaAtivosPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog);
	List<ItemBacklog> consultarSprintBacklog(Integer sprintID, Integer usuarioLogadoID);
	List<SprintBacklog> consultarAtivoPorItem(ItemBacklog item);
	List<SprintBacklog> listarAtivosPorSprint(Sprint sprint);
	List<Tarefa> consultarTarefasPorSprint(Integer sprintID);
	Long totalDeHorasEstimadasDaSprint(Integer sprintID);
	Long totalDeHorasRestantesDaSprintPorData(Integer sprintID, String data);
}