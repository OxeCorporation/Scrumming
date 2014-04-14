package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.repositorio.SprintBacklogRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintBacklogChave;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;

@Service
public class SprintBacklogManager extends AbstractManager<SprintBacklog, SprintBacklogChave> implements ISprintBacklogManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SprintBacklogRepositorio sprintBacklogRepositorio;
	
	@Autowired
	private ITarefaManager tarefaManager;
	
	@Autowired
	private ISprintManager sprintManager;
	
	@Override
	public SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		return sprintBacklogRepositorio.consultaPorChaveComposta(sprint, itemBacklog);
	}
	
	@Override
	public SprintBacklog consultaAtivosPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		return sprintBacklogRepositorio.consultaAtivosPorChaveComposta(sprint, itemBacklog);
	}

	@Override
	public List<ItemBacklog> consultarItensBacklogPorSprint(Integer sprintID) {
		return sprintBacklogRepositorio.consultarItensSprintBacklogPorSprint(sprintID);
	}
	
	@Override
	public List<SprintBacklog> listarAtivosPorSprint(Sprint sprint) {
		return sprintBacklogRepositorio.listarAtivosPorSprint(sprint);
	}
	
	@Override
	public List<SprintBacklog> consultarAtivoPorItem(ItemBacklog item) {
		return sprintBacklogRepositorio.consultarAtivoPorItem(item);
	}
	
	/**
	 * consulta a lista de itens e suas respectivas tarefas de uma Sprint para
	 * ser exibida na tela.
	 */
	public List<ItemBacklog> consultarSprintBacklog(Integer sprintID) {
		Sprint sprint = sprintManager.findByKey(sprintID);
		// Lista que será retornada à tela
		// Busca a lista dos Itens de Backlog de uma Sprint.
		List<ItemBacklog> itemsDaSprint = new ArrayList<>();
		itemsDaSprint = consultarItensBacklogPorSprint(sprintID);

		if (itemsDaSprint.size() > 0) {
			// Para cada item
			for (ItemBacklog itemBacklog : itemsDaSprint) {
				itemBacklog.setDeliverable(true);
				itemBacklog.setStatusItembacklog("");
				if (sprint.getSituacaoSprint() == SituacaoSprintEnum.FECHADA) {
					itemBacklog.setDeliverable(false);
				} else if (itemBacklog.getSituacaoBacklog() == SituacaoItemBacklogEnum.FEITO) {
					itemBacklog.setDeliverable(false);
					itemBacklog.setStatusItembacklog("Entregue");
				}				
				// Seta a lista de tarefas desse item.
				List<Tarefa> tarefas = tarefaManager.consultarPorItemBacklog(itemBacklog.getCodigo());
				itemBacklog.setTarefas(tarefas);
			}
		}
		return itemsDaSprint;
	}
	
	/**
	 * Associa um Item de Backlog a uma Sprint.
	 */
	public void associarItemASprint(Sprint sprintPersistido, List<ItemBacklog> itensBacklogSprint) {
		
		// Para cada itemBacklog associado à sprint será setado o projeto e a Sprint
		for (ItemBacklog item : itensBacklogSprint) {
			
			// Cria um objeto do SprintBacklog
			SprintBacklog sprintBacklog = new SprintBacklog();
			
			// Define o SprintBacklog
			sprintBacklog.setSprint(sprintPersistido);
			sprintBacklog.setItemBacklog(item);
			sprintBacklog.setAtivo(true);
			
			insertOrUpdate(sprintBacklog);
			
			// Atribui um item do ItemBacklog à Sprint
			/*SprintBacklog sprintBacklogBusca1;
			sprintBacklogBusca1 = consultaPorChaveComposta(sprintPersistido, item1);
			
			if (sprintBacklogBusca1 != null && sprintBacklogBusca1.isAtivo() == true) {
				sprintBacklogBusca1.setAtivo(true);
				insertOrUpdate(sprintBacklogBusca1);
			} else {
				insertOrUpdate(sprintBacklog1);
			}*/
		}
	}
	
	/**
	 * Retira um item de Backlog da Sprint
	 */
	@Override
	public void desassociarItemASprint(Sprint sprintPersistido, List<ItemBacklog> itensBacklogProduto) {
		
		// Retira um item da SprintBacklog
		for (ItemBacklog item : itensBacklogProduto){
			
			SprintBacklog sprintBacklogBusca;
			sprintBacklogBusca = consultaPorChaveComposta(sprintPersistido, item);
			
			if (item.getSituacaoBacklog() != SituacaoItemBacklogEnum.FEITO && sprintBacklogBusca != null && sprintBacklogBusca.isAtivo() == true) {
				sprintBacklogBusca.setAtivo(false);
				insertOrUpdate(sprintBacklogBusca);
			}
		}		
	}

	/* getters and setters */
	@Override
	public AbstractRepositorio<SprintBacklog, SprintBacklogChave> getRepositorio() {
		return this.sprintBacklogRepositorio;
	}

	public SprintBacklogRepositorio getSprintBacklogRepositorio() {
		return sprintBacklogRepositorio;
	}

	public void setSprintBacklogRepositorio(SprintBacklogRepositorio sprintBacklogRepositorio) {
		this.sprintBacklogRepositorio = sprintBacklogRepositorio;
	}
}
