package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class SprintClientService extends AbstractClientService {

	/**
	 * Função para enviar os dados da tela Sprint para serem presistidas no banco de dados.
	 * @param sprintDTO Objeto com os dados da transção de salvar na tela de Sprint.
	 * @return tipo de retorno a ser definido.
	 */
	public String salvarSprint(SprintDTO sprintDTO) {
		return getRestTemplate().postForObject(getURIService(ConstantesService.Sprint.URL_SALVAR), sprintDTO, String.class);
	}

	/**
	 * Consulta para obter uma lista de todas as Sprints de um projeto.
	 * @param projetoID Chave do projeto
	 * @return lista de Sprints
	 */
	public List<Sprint> consultarSprintsPorProjeto(Integer projetoID) {
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URL_CONSULTAR_POR_PROJETO), Sprint[].class, projetoID));
	}
	
	/**
	 * Consulta os itens de backlog que estão disponíveis para serem associados à uma sprint.
	 * @param projetoID
	 * @return
	 */
	public List<ItemBacklog> consultarItensDisponiveis(Integer projetoID) {
		ResponseEntity<ItemBacklog[]> forEntity = getRestTemplate().getForEntity(getURIService(ConstantesService.Sprint.URL_CONSULTAR_ITENS_DISPONIVEIS), ItemBacklog[].class, projetoID);
		return Arrays.asList(forEntity.getBody());
	}
	
	/**
	 * Função para consultar a sprintBacklog
	 * @param sprintID
	 * @return
	 */
	public List<ItemBacklog> consultarSprintBacklog(Integer sprintID, Integer usuarioLogadoID) {
		ResponseEntity<ItemBacklog[]> forEntity = getRestTemplate().getForEntity(
				getURIService(ConstantesService.Sprint.URL_CONSULTAR_SPRINT_BACKLOG), 
				ItemBacklog[].class, sprintID, usuarioLogadoID);
		return Arrays.asList(forEntity.getBody());
	}
	
	/**
	 * Consulta para objer e exibir os dados da tela de cadastro da Sprint.
	 * @param sprintID Chave da Sprint.
	 * @return Objeto DTO que representa os dados da tela da Sprint.
	 */
	public SprintDTO consultarSprintDTO(Integer sprintID) {
		return getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URL_CONSULTAR_SPRINT_DTO), SprintDTO.class, sprintID);
	}
	
	/**
	 * Efetua o fechamento de uma Sprint.
	 * @param sprint
	 */
	public void fecharSprint(Sprint sprint) {
		getRestTemplate().postForObject(getURIService(ConstantesService.Sprint.URL_FECHAR_SPRINT), sprint, void.class);
	}
	/**
	 * Consulta as tarefas por Sprint
	 * 
	 * @param sprintID
	 * @return List<Tarefa>
	 */
	public List<Tarefa> consultarTarefasPorSprint(Integer sprintID){
		Tarefa[] tarefas = getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URI_CONSULTAR_TEREFAS), Tarefa[].class, sprintID);
		return Arrays.asList(tarefas);
	}
	
	public Long totalDeHorasEstimadasDaSprint(Integer sprintID) {
		return getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URL_TOTAL_DE_HORAS_ESTIMADAS), Long.class, sprintID);
	}
	
	public Sprint consultarSprint(Integer sprintID) {
		return getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URL_CONSULTAR_SPRINT), Sprint.class, sprintID);
	}
	
	public Long totalDeHorasRestantesDaSprintPorData(Integer sprintID, DateTime data) {
		return getRestTemplate().getForObject(getURIService(ConstantesService.Sprint.URL_TOTAL_DE_HORAS_RESTANTES_POR_DATA), Long.class, sprintID, data);
	}
}