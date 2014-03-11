package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;

public class SprintClientService {

	// O que eu quero
	public void salvarSprint(Sprint sprint, List<ItemBacklog> sprintBacklog, List<ItemBacklog> productBacklog) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/{sprint}/itemSprint/{sprintBacklog}/itemBacklog/{productBacklog}";

		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, sprint, sprintBacklog, productBacklog);
	}
	
	// Teste1
	public String salvarSprintTeste1(SprintDTO sprint) {
		
		RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        
        String uri = "http://localhost:8080/Scrumming/service/sprint/save";
        
        return rt.postForObject(uri, sprint, String.class);
	}
	
	// Teste2
	public void salvarSprintTeste2(List<ItemBacklog> sprintBacklog) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/item/{sprintBacklog}";

		restTemplate.postForEntity(url, HttpEntity.EMPTY, ItemBacklog[].class, sprintBacklog);		
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
	
	public void fecharSprint(Sprint sprint) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/sprint/{sprint}";
		
		restTemplate.put(url, HttpEntity.EMPTY, sprint);
	}
}