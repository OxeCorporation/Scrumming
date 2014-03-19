package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
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

		return Arrays.asList(getRestTemplate().getForObject(ConstantesService.Sprint.URL_CONSULTAR_POR_PROJETO, Sprint[].class, projetoID));
	}
	
	/**
	 * Consulta para objer e exibir os dados da tela de cadastro da Sprint.
	 * @param sprintID Chave da Sprint.
	 * @return Objeto DTO que representa os dados da tela da Sprint.
	 */
	public SprintDTO consultarSprintDTO(Integer sprintID) {
		return getRestTemplate().getForObject(ConstantesService.Sprint.URL_CONSULTAR_SPRINT_DTO, SprintDTO.class, sprintID);
	}
	
	/**
	 * Efetua o fechamento de uma Sprint.
	 * @param sprint
	 */
	public void fecharSprint(Sprint sprint) {
		getRestTemplate().put(ConstantesService.Sprint.URL_FECHAR_SPRINT, HttpEntity.EMPTY, sprint);
	}
}