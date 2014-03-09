package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.repositorio.SprintBacklogRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;

@Service
public class SprintBacklogManager extends AbstractManager<SprintBacklog, Integer> implements ISprintBacklogManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SprintBacklogRepositorio sprintBacklogRepositorio;
	
	@Override
	public SprintBacklog consultaPorChaveComposta(Sprint sprint, ItemBacklog itemBacklog) {
		return consultaPorChaveComposta(sprint, itemBacklog);
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
			
			if (sprintBacklogBusca != null && sprintBacklogBusca.isAtivo() == true && item.getSituacaoBacklog() != SituacaoItemBacklogEnum.ATIVO) {
				sprintBacklogBusca.setAtivo(false);
			}
		}		
	}

	/* getters and setters */
	@Override
	public AbstractRepositorio<SprintBacklog, Integer> getRepositorio() {
		return this.sprintBacklogRepositorio;
	}

	public SprintBacklogRepositorio getSprintBacklogRepositorio() {
		return sprintBacklogRepositorio;
	}

	public void setSprintBacklogRepositorio(SprintBacklogRepositorio sprintBacklogRepositorio) {
		this.sprintBacklogRepositorio = sprintBacklogRepositorio;
	}
}