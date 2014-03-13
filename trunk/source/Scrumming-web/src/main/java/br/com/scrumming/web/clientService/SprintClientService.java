package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;

public class SprintClientService {

	/**
	 * Função para enviar os dados da tela Sprint para serem presistidas no banco de dados.
	 * @param sprintDTO Objeto com os dados da transção de salvar na tela de Sprint.
	 * @return tipo de retorno a ser definido.
	 */
	public String salvarSprint(SprintDTO sprintDTO) {
		
		RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        
        String uri = "http://localhost:8080/Scrumming/service/sprint/save";
        
        return rt.postForObject(uri, sprintDTO, String.class);
	}

	/**
	 * Consulta para obter uma lista de todas as Sprints de um projeto.
	 * @param projetoID Chave do projeto
	 * @return lista de Sprints
	 */
	public List<Sprint> consultarSprintsPorProjeto(Integer projetoID) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/list/{projetoID}";

		ResponseEntity<Sprint[]> sprints = restTemplate.getForEntity(url, Sprint[].class, projetoID);

		return Arrays.asList(sprints.getBody());
	}
	
	/**
	 * Consulta para objer e exibir os dados da tela de cadastro da Sprint.
	 * @param sprintID Chave da Sprint.
	 * @return Objeto DTO que representa os dados da tela da Sprint.
	 */
	public SprintDTO consultarSprintDTO(Integer sprintID) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/sprint/{sprintID}";
		
		ResponseEntity<SprintDTO> sprintDTO = restTemplate.getForEntity(url, SprintDTO.class, sprintID);

		return sprintDTO.getBody();
	}
	
	/**
	 * Efetua o fechamento de uma Sprint.
	 * @param sprint
	 */
	public void fecharSprint(Sprint sprint) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/sprint/{sprint}";
		
		restTemplate.put(url, HttpEntity.EMPTY, sprint);
	}
}