package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Projeto;

public class ProjetoClientService {

	public void salvarProjeto(Projeto projeto) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		String url = "http://localhost:8080/Scrumming/service/projeto/{salvarProjeto}";

		restTemplate.postForEntity(url, requestEntity, null, projeto);

	}

	public List<Projeto> consultartodosProjetos() {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/projeto/{allProjeto}";

		ResponseEntity<Projeto[]> projetos = restTemplate.getForEntity(url,
				Projeto[].class);

		
		return Arrays.asList(projetos.getBody());
	}
	
	public void deletarProjeto(Projeto projeto) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/projeto/list/{projetoID}";

		restTemplate.delete(url, projeto);
	}

	
}