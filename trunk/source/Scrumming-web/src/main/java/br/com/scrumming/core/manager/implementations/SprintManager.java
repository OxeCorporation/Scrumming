package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.repositorio.SprintBacklogRepositorio;
import br.com.scrumming.core.repositorio.SprintRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;

@Service
public class SprintManager extends AbstractManager<Sprint, Integer> implements
		ISprintManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SprintRepositorio sprintRepositorio;
	
	@Autowired
	private SprintBacklogRepositorio sprintBacklogRepositorio; 

	@Override
	public AbstractRepositorio<Sprint, Integer> getRepositorio() {
		return this.sprintRepositorio;
	}

	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		return sprintRepositorio.consultarPorProjeto(projetoID);
	}

	@Override
	public void salvarSprint(Sprint sprint, List<ItemBacklog> itensBacklog) {

		// Persiste o objeto Sprint e retorna a chave.
		Integer sprintID = insertOrUpdate(sprint);
		// Caso sera inserido ou alterado a Sprint
		if (sprintID != null) {
			// Busca o objeto persistido pela chave.
			Sprint sprintPersistido = findByKey(sprintID);
			// Para cada itemBacklog associado à sprint será setado o projeto e a Sprint
			for (ItemBacklog item : itensBacklog) {
				// Cria um objeto do SprintBacklog
				SprintBacklog sprintBacklog = new SprintBacklog();
				// Define o SprintBacklog
				sprintBacklog.setSprint(sprintPersistido);
				sprintBacklog.setItemBacklog(item);
				// Efetua uma consulta
				SprintBacklog sprintBacklogBusca = new SprintBacklog();
				if (sprintBacklogBusca.isAtivo() == true) {
					sprintBacklogBusca.setAtivo(false);
					sprintBacklogRepositorio.insertOrUpdate(sprintBacklogBusca);
				} else {
					sprintBacklogRepositorio.insertOrUpdate(sprintBacklog);
				}
			}
		}		
	}

	public void fecharSprint(Sprint sprint) {
		// TODO: Efetuar a validação para o fechamento da Sprint(Será efetuado e
		// testado dia 09/03);
	}

	/* getters and setters */
	public SprintRepositorio getSprintRepositorio() {
		return sprintRepositorio;
	}

	public void setSprintRepositorio(SprintRepositorio sprintRepositorio) {
		this.sprintRepositorio = sprintRepositorio;
	}
}