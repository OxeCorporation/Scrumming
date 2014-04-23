package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.repositorio.SprintRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
	
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
	
	@Autowired
	private ITarefaManager tarefaManager;

	@Override
	public AbstractRepositorio<Sprint, Integer> getRepositorio() {
		return this.sprintRepositorio;
	}

	/**
	 * Consulta as Sprints de um Projeto que serão exibidos na tela inicial da Sprint.
	 */
	@Override
	public List<Sprint> consultarPorProjeto(Integer projetoID) {
		List<Sprint> sprints = sprintRepositorio.consultarPorProjeto(projetoID);
		List<Sprint> sprintToShow = new ArrayList<>();
		for (Sprint sprint : sprints) {
			sprint.setEditable(true);
			if (sprint.getSituacaoSprint() == SituacaoSprintEnum.FECHADA) {
				sprint.setStatusSprint("Fechada");
				sprint.setEditable(false);
			} else if (DateTime.now().isAfter(sprint.getDataInicio()) && DateTime.now().isBefore(sprint.getDataFim())) {
				sprint.setStatusSprint("Atual");
			} else if (DateTime.now().isAfter(sprint.getDataFim()) && sprint.getSituacaoSprint() == SituacaoSprintEnum.ABERTA) {
				sprint.setStatusSprint("Expirada");				
			} else {
				sprint.setStatusSprint("");
			}
			sprintToShow.add(sprint);
		}
		return sprintToShow;
	}

	/**
	 * Salva ou altera a sprint e define a sprintbacklog.
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String salvarSprint(SprintDTO sprintDTO) {

		// Prepara os dados para a persistência.
		Sprint sprint = sprintDTO.getSprint();
		sprint.setDataInicio(new DateTime(sprintDTO.getDataInicio()));
		sprint.setDataFim(new DateTime(sprintDTO.getDataFim()));
		sprint.setDataRevisao(new DateTime(sprintDTO.getDataRevisao()));
		sprint.setSituacaoSprint(SituacaoSprintEnum.ABERTA);
		sprint.setDataCadastro(new DateTime());
		
		List<Sprint> sprints = sprintRepositorio.consultaTodasComExcessao(sprint.getProjeto().getCodigo(), sprint.getCodigo());
		for (Sprint sprint2 : sprints) {
			if (sprint.getDataInicio().isBefore(sprint2.getDataFim()) && sprint2.getCodigo() != sprint.getCodigo()) {
				throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DATA_SPRINT_EXISTE);
			}
		}
		
		if (sprint.getDataFim().isBefore(sprint.getDataInicio())) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DATA_FIM_MENOR_INICIO_SPRINT);
		}
		
		if (sprint.getDataRevisao().isBefore(sprint.getDataFim())) {
			throw new NegocioException(ConstantesMensagem.MENSAGEM_ERRO_DATA_REVISAO_MENOR_FIM_SPRINT);
		}
		
		// Persiste o objeto Sprint e retorna a chave.
		Integer sprintID = insertOrUpdate(sprint);

		List<ItemBacklog> itensBacklogSprint = sprintDTO.getSprintBacklog();
		List<ItemBacklog> itensBacklogProduto = sprintDTO.getProductBacklog();
		
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
		return "";
	}

	/**
	 * Consulta os dados que serão exibidos nos campos da tela de Cadastro de Sprint
	 */
	@Override
	@Transactional(readOnly=true)
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
		sprintDTO.setDataInicio(sprint.getDataInicio().toDate());
		sprintDTO.setDataFim(sprint.getDataFim().toDate());
		sprintDTO.setDataRevisao(sprint.getDataRevisao().toDate());

		// Seta a lista de itens ativos que representam o SprintBacklog
		sprintBacklog = sprintBacklogManager.consultarItensBacklogPorSprint(sprintID);
		sprintDTO.setSprintBacklog(sprintBacklog);

		// Pesquisa todos os itens do Product Backlog
		List<ItemBacklog> productBacklog = new ArrayList<>();
		productBacklog = itemBacklogManager.consultarItensDisponiveisPorProjeto(sprint.getProjeto().getCodigo());

		// Percorre todos os itens do backlog para verificar os que não foram
		// atribuidos às Sprints
		for (ItemBacklog item : productBacklog) {
			SprintBacklog spBacklog = sprintBacklogManager.consultaAtivosPorChaveComposta(sprint, item);
			if (spBacklog == null) {
				itensDisponiveis.add(item);
			}
		}
		sprintDTO.setProductBacklog(itensDisponiveis);
		return sprintDTO;
	}

	/**
	 * Função para gerenciar o fechamento da Sprint
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void fecharSprint(Sprint sprint) {

		Sprint sprintConsulta = findByKey(sprint.getCodigo());
		
		List<SprintBacklog> itens = sprintBacklogManager.listarAtivosPorSprint(sprintConsulta);
		
		for (SprintBacklog item : itens) {
			
			ItemBacklog itemBacklog = itemBacklogManager.findByKey(item.getItemBacklog().getCodigo());
			
			if (itemBacklog.getSituacaoBacklog() != SituacaoItemBacklogEnum.ENTREGUE) {
				
				List<Tarefa> tarefas = tarefaManager.consultarPorItemBacklogIhNotSituacao(itemBacklog.getCodigo(), SituacaoTarefaEnum.FEITO);
				
				if (tarefas.size() == 0) {
					itemBacklog.setSituacaoBacklog(SituacaoItemBacklogEnum.ENTREGUE);
					itemBacklogManager.insertOrUpdate(itemBacklog);
				}
				item.setAtivo(false);
				sprintBacklogManager.insertOrUpdate(item);
			}
		}
		sprint = findByKey(sprint.getCodigo());
		sprint.setSituacaoSprint(SituacaoSprintEnum.FECHADA);
		sprint.setDataFechamento(DateTime.now());
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