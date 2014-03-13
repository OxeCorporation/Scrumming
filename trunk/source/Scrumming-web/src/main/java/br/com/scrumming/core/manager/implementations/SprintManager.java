package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.repositorio.SprintRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;

@Service
public class SprintManager extends AbstractManager<Sprint, Integer> implements ISprintManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SprintRepositorio sprintRepositorio;
	
	@Autowired
	private ISprintBacklogManager sprintBacklogManager;
	
	@Autowired
	private IItemBacklogManager itemBacklogManager;

	@Override
	public AbstractRepositorio<Sprint, Integer> getRepositorio() {
		return this.sprintRepositorio;
	}

	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		return sprintRepositorio.consultarPorProjeto(projetoID);
	}

	@Override
	public String salvarSprint(SprintDTO sprintDTO) {

		String retorno = "";
		Sprint sprint = sprintDTO.getSprint();
		List<ItemBacklog> itensBacklogSprint = sprintDTO.getSprintBacklog();
		List<ItemBacklog> itensBacklogProduto = sprintDTO.getProductBacklog();
		
		// Persiste o objeto Sprint e retorna a chave.
		Integer sprintID = insertOrUpdate(sprint);
		
		// Caso sera inserido ou alterado a Sprint
		if (sprintID != null) {
			
			retorno = "Registro foi salvo";
			// Busca o objeto persistido pela chave.
			Sprint sprintPersistido = findByKey(sprintID);
			
			if (CollectionUtils.isNotEmpty(itensBacklogSprint)) {
				sprintBacklogManager.associarItemASprint(sprintPersistido, itensBacklogSprint);
			}
			if (CollectionUtils.isNotEmpty(itensBacklogProduto)) {
				sprintBacklogManager.desassociarItemASprint(sprintPersistido, itensBacklogProduto);
			}
		}
		return retorno;
	}
	
	public SprintDTO consultarSprintDTO(Integer sprintID) {
		
		// Cria o DTO que será enviado à tela para exibição
		SprintDTO sprintDTO = new SprintDTO();
		// Seta a Sprint
		sprintDTO.setSprint(findByKey(sprintID));
		// Seta a lista de itens que representa o SprintBacklog
		sprintDTO.setSprintBacklog(itemBacklogManager.consultarPorSprintBacklog(sprintDTO.getSprint()));
		// Pesquisa todos os itens do Product Backlog
		List<ItemBacklog> productBacklog;
		productBacklog = itemBacklogManager.consultarPorProjeto(sprintDTO.getSprint().getProjeto().getChave());
		// Percorre todos os itens do backlog para verificar os que não foram atribuidos às Sprints
		for	(ItemBacklog item : productBacklog) {
			// Busca a lista das Sprints do projeto
			List<Sprint> sprints = consultarPorProjeto(sprintDTO.getSprint().getProjeto().getChave());
			// Consulta os Itens de cada SprintBacklog
			for (Sprint sprint : sprints) {
		    	List<ItemBacklog> itensDaSprint = itemBacklogManager.consultarPorSprintBacklog(sprint);
		    	// Para cada lista de SprintBacklog
		        for (ItemBacklog sprintBacklog : itensDaSprint) {
		        	// Aciciona a lista, apenas os diponiveis
		        	if (sprintBacklog == null) {
						sprintDTO.getProductBacklog().add(item);
					}
		        }
	    	}
		}
		return sprintDTO;
	}

	/**
	 * Função para gerenciar o fechamento da Sprint
	 */
	public void fecharSprint(Sprint sprint) {
		
		List<SprintBacklog> itens = (List<SprintBacklog>) sprintBacklogManager.consultarPorCampo("codigo", sprint.getChave());
		for (SprintBacklog item : itens) {
			
			item.setAtivo(false);
		}
		sprint.setSituacaoSprint(SituacaoSprintEnum.FECHADA);
		insertOrUpdate(sprint);
	}

	/* getters and setters */
	public SprintRepositorio getSprintRepositorio() {
		return sprintRepositorio;
	}

	public void setSprintRepositorio(SprintRepositorio sprintRepositorio) {
		this.sprintRepositorio = sprintRepositorio;
	}
}