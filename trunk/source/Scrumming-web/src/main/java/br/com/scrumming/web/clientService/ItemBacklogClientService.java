package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.ItemBacklog;

public class ItemBacklogClientService {
	
	public void salvarItemBacklog(ItemBacklog itemBacklog) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/itemBacklog/{itemBacklog}";

		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, itemBacklog);
	}
	
	public List<ItemBacklog> consultarItemPorProjeto(Integer projetoID) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/Scrumming/service/itemBacklog/list/{projetoID}";

		ResponseEntity<ItemBacklog[]> listaDeItens = restTemplate.getForEntity(url, ItemBacklog[].class, projetoID);

		return Arrays.asList(listaDeItens.getBody());
	}
	
	public void cancelarItemBacklog(ItemBacklog item) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/itemBacklog/{item}";
		
		restTemplate.postForEntity(url, HttpEntity.EMPTY, void.class, item);
	}
	
	public ItemBacklog consultarItemPorID(Integer itemID) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/itemBacklog/{itemID}";
		
		ResponseEntity<ItemBacklog> itemBacklog = restTemplate.getForEntity(url, ItemBacklog.class, itemID);

		return itemBacklog.getBody();
	}
}
