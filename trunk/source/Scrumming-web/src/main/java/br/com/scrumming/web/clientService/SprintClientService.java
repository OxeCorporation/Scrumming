package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

public class SprintClientService {

	public void salvarSprint(Sprint sprint, List<ItemBacklog> itensBacklog) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		String url = "http://localhost:8080/Scrumming/service/sprint/{sprint}/item/{itensBacklog}";

		restTemplate.postForEntity(url, requestEntity, null, sprint,
				itensBacklog);

	}

	public List<Sprint> consultarPorProjeto(Integer projetoID) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/sprint/list/{projetoID}";

		ResponseEntity<Sprint[]> sprints = restTemplate.getForEntity(url,
				Sprint[].class, projetoID);

		return Arrays.asList(sprints.getBody());
	}
	
}