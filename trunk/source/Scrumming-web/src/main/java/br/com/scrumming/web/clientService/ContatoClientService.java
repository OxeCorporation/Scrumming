package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Contato;

public class ContatoClientService {

	public List<Contato> findAll() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Contato[]> contatos = restTemplate.getForEntity(
				"http://localhost:8080/Scrumming/service/contato",
				Contato[].class);
		return Arrays.asList(contatos.getBody());
	}
}
