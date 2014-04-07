package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
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
public class SprintManager extends AbstractManager<Sprint, Integer> implements
		ISprintManager {

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

	// 01
	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		return sprintRepositorio.consultarPorProjeto(projetoID);
	}

	// 04
	@Override
	public String salvarSprint(SprintDTO sprintDTO) {

		String retorno = "";
		Sprint sprint = sprintDTO.getSprint();
		sprint.setDataInicio(new DateTime(sprintDTO.getDataInicio()));
		sprint.setDataRevisao(new DateTime(sprintDTO.getDataRevisao()));
		sprint.setSituacaoSprint(SituacaoSprintEnum.ABERTA);
		sprint.setDataCadastro(new DateTime());
		//List<ItemBacklog> itensBacklogSprint = sprintDTO.getSprintBacklog();
		//List<ItemBacklog> itensBacklogProduto = sprintDTO.getProductBacklog();
		sprint.setDataFim(sprint.getDataInicio().plusDays(sprintDTO.getDias()));
		// Persiste o objeto Sprint e retorna a chave.
		Integer sprintID = insertOrUpdate(sprint);

		// Caso sera inserido ou alterado a Sprint
		if (sprintID != null) {

			retorno = "Registro foi salvo";
			// Busca o objeto persistido pela chave.
			Sprint sprintPersistido = findByKey(sprintID);

			/*if (CollectionUtils.isNotEmpty(itensBacklogSprint)) {
				// TODO: Testar se funciona
				sprintBacklogManager.associarItemASprint(sprintPersistido,
						itensBacklogSprint);
			}
			if (CollectionUtils.isNotEmpty(itensBacklogProduto)) {
				// TODO: Testar se funciona
				sprintBacklogManager.desassociarItemASprint(sprintPersistido,
						itensBacklogProduto);
			}*/
		}
		return retorno;
	}

	// 05, 06, 07
	@Override
	public SprintDTO consultarSprintDTO(Integer sprintID) {

		// Cria o DTO que será enviado à tela para exibição
		SprintDTO sprintDTO = new SprintDTO();

		// Objetos que serão setados do DTO da Sprint
		Sprint sprint = new Sprint();
		List<ItemBacklog> itensDisponiveis = new ArrayList<>();
		List<ItemBacklog> sprintBacklog = new ArrayList<>();

		// Seta a Sprint
		sprint = findByKey(sprintID);
		sprintDTO.setSprint(sprint);

		// Seta a lista de itens ativos que representam o SprintBacklog
		sprintBacklog = sprintBacklogManager
				.consultarItensAtivosBacklogPorSprint(sprintID);
		sprintDTO.setSprintBacklog(sprintBacklog);

		// Pesquisa todos os itens do Product Backlog
		List<ItemBacklog> productBacklog = new ArrayList<>();
		productBacklog = itemBacklogManager.consultarPorProjeto(sprintDTO
				.getSprint().getProjeto().getChave());

		// Percorre todos os itens do backlog para verificar os que não foram
		// atribuidos às Sprints
		for (ItemBacklog item : productBacklog) {
			SprintBacklog spBacklog = sprintBacklogManager
					.consultaAtivosPorChaveComposta(sprint, item);
			if (spBacklog == null) {
				itensDisponiveis.add(item);
			}
		}
		sprintDTO.setProductBacklog(itensDisponiveis);
		return sprintDTO;
	}

	/**
	 * consulta a lista de itens e suas respectivas tarefas de uma Sprint para
	 * ser exibida na tela.
	 */
	/*public List<ItemsTasksDTO> consultarItemsAndTasksDTO(Integer sprintID) {
		// Lista que será retornada à tela
		List<ItemsTasksDTO> listaDTO = new ArrayList<>();
		// Busca a lista dos Itens de Backlog de uma Sprint.
		List<ItemBacklog> itemsDaSprint = new ArrayList<>();
		itemsDaSprint = sprintBacklogManager
				.consultarItensAtivosBacklogPorSprint(sprintID);

		if (itemsDaSprint.size() > 0) {
			// Para cada item
			for (ItemBacklog itemBacklog : itemsDaSprint) {
				// Instancia um DTO que será adicionado à lista.
				ItemsTasksDTO itDTO = new ItemsTasksDTO();
				// Seta o item ao DTO
				itDTO.setItem(itemBacklog);
				// Seta a lista de tarefas desse item ao DTO.
				itDTO.setTarefas(tarefaManager
						.consultarPorItemBacklog(itemBacklog.getChave()));
				listaDTO.add(itDTO);
			}
		}
		return listaDTO;
	}*/

	/**
	 * Função para gerenciar o fechamento da Sprint
	 */
	@Override
	public void fecharSprint(Integer sprintID) {

		List<SprintBacklog> itens = (List<SprintBacklog>) sprintBacklogManager
				.consultarPorCampo("codigo", sprintID);
		for (SprintBacklog item : itens) {

			item.setAtivo(false);
		}
		Sprint sprint = new Sprint();
		sprint = findByKey(sprintID);
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