package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

public class SprintClientService {

	public void salvarSprint(Sprint sprint, List<ItemBacklog> sprintBacklog, List<ItemBacklog> productBacklog) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/{sprint}/itemSprint/{itensBacklogSprint}/itemBacklog/{itensBacklogProduto}";

		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, sprint, sprintBacklog, productBacklog);
	}

	public List<Sprint> consultarSprintsPorProjeto(Integer projetoID) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/list/{projetoID}";

		ResponseEntity<Sprint[]> sprints = restTemplate.getForEntity(url, Sprint[].class, projetoID);

		return Arrays.asList(sprints.getBody());
	}
	
	public Sprint consultarSprint(Integer sprintID) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/sprint/{sprintID}";
		
		ResponseEntity<Sprint> sprint = restTemplate.getForEntity(url, Sprint.class, sprintID);

		return sprint.getBody();
	}
	
	public void fecharSprint(Sprint sprint, List<ItemBacklog> sprintBacklog, List<ItemBacklog> productBacklog) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/sprint/{sprintID}";
		
		restTemplate.put(url, HttpEntity.EMPTY, sprint, sprintBacklog, productBacklog);
	}
}