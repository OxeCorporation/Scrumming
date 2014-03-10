package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Tarefa;

public class TarefaClientService {
	
	public void salvarTarefa(Tarefa tarefa) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";

		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, tarefa);
	}
	
	public List<Tarefa> consultarTarefasPorItemBacklog(Integer itemBacklogID) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/tarefa/list/{itemBacklogID}";

		ResponseEntity<Tarefa[]> tarefas = restTemplate.getForEntity(url, Tarefa[].class, itemBacklogID);

		return Arrays.asList(tarefas.getBody());
	}
	
	public Tarefa consultarTarefa(Integer tarefaID) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/tarefa/{tarefaID}";
		
		ResponseEntity<Tarefa> tarefa = restTemplate.getForEntity(url, Tarefa.class, tarefaID);

		return tarefa.getBody();
	}
	
	public void removerTarefa(Tarefa tarefa) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/tarefa/{tarefa}";
		
		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, tarefa);
	}
}
