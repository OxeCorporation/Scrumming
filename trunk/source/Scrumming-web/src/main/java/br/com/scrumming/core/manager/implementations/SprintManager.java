package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.repositorio.SprintRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

@Service
public class SprintManager extends AbstractManager<Sprint, Integer> implements
		ISprintManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SprintRepositorio sprintRepositorio;
	
	private ISprintBacklogManager sprintBacklogManager = new SprintBacklogManager();

	@Override
	public AbstractRepositorio<Sprint, Integer> getRepositorio() {
		return this.sprintRepositorio;
	}

	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		return sprintRepositorio.consultarPorProjeto(projetoID);
	}

	@Override
	public void salvarSprint(Sprint sprint, List<ItemBacklog> itensBacklogSprint, List<ItemBacklog> itensBacklogProduto) {

		// Persiste o objeto Sprint e retorna a chave.
		Integer sprintID = insertOrUpdate(sprint);
		
		// Caso sera inserido ou alterado a Sprint
		if (sprintID != null) {
			
			// Busca o objeto persistido pela chave.
			Sprint sprintPersistido = findByKey(sprintID);
			
			if (CollectionUtils.isNotEmpty(itensBacklogSprint)) {
				sprintBacklogManager.associarItemASprint(sprintPersistido, itensBacklogSprint);
			}
			if (CollectionUtils.isNotEmpty(itensBacklogProduto)) {
				sprintBacklogManager.desassociarItemASprint(sprintPersistido, itensBacklogProduto);
			}
		}
	}

	public void fecharSprint(Sprint sprint, List<ItemBacklog> sprintBacklog, List<ItemBacklog> productBacklog) {
		
		
	}

	/* getters and setters */
	public SprintRepositorio getSprintRepositorio() {
		return sprintRepositorio;
	}

	public void setSprintRepositorio(SprintRepositorio sprintRepositorio) {
		this.sprintRepositorio = sprintRepositorio;
	}
}