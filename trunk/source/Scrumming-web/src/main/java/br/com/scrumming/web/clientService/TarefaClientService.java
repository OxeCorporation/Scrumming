package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TarefaClientService extends AbstractClientService {
	
	public void salvarTarefa(Tarefa tarefa) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(ConstantesService.Tarefa.URL_SALVAR, 
								   HttpEntity.EMPTY, void.class, tarefa);
	}
	
	public List<Tarefa> consultarTarefasPorItemBacklog(Integer itemBacklogID) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Tarefa[]> tarefas = restTemplate.getForEntity(ConstantesService.Tarefa.URL_CONSULTAR_POR_ITEM_BACKLOG, Tarefa[].class, itemBacklogID);

		return Arrays.asList(tarefas.getBody());
	}
	
	public Tarefa consultarTarefa(Integer tarefaID) {
		
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<Tarefa> tarefa = restTemplate.getForEntity(ConstantesService.Tarefa.URL_CONSULTAR, Tarefa.class, tarefaID);

		return tarefa.getBody();
	}
	
	public void removerTarefa(Tarefa tarefa) {
		
		RestTemplate restTemplate = new RestTemplate();		
		restTemplate.postForEntity(ConstantesService.Tarefa.URL_REMOVER, HttpEntity.EMPTY, void.class, tarefa);
	}
}
