package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class ItemBacklogClientService extends AbstractClientService{
	
	
	public void salvarItemBacklog(ItemBacklog itemBacklog) {
		
		getRestTemplate().postForObject(
				getURIService(ConstantesService.ItemBacklog.URL_SALVAR),
				itemBacklog, void.class);
	}
	
	public List<ItemBacklog> consultarItemPorProjeto(Integer projetoID) {

		ResponseEntity<ItemBacklog[]> forEntity = getRestTemplate().
									getForEntity(getURIService(ConstantesService.
									ItemBacklog.URL_CONSULTAR_POR_PROJETO), ItemBacklog[].class, projetoID);
		return Arrays.asList(forEntity.getBody());
	}
	
	public List<ItemBacklog> consultarSituacaoDoItemPorProjeto(Integer projetoID){
		ResponseEntity<ItemBacklog[]> forEntity = getRestTemplate().
				getForEntity(getURIService(ConstantesService.
				ItemBacklog.URL_CONSULTAR_SITUACAO_POR_PROJETO), ItemBacklog[].class, projetoID);
		return Arrays.asList(forEntity.getBody());
	}
	
	public void cancelarItemBacklog(ItemBacklog item) {
		
		getRestTemplate().postForObject(
				getURIService(ConstantesService.ItemBacklog.URL_CANCELAR),
				item, void.class);
	}
	
	public ItemBacklog consultarItemPorID(Integer itemID) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://localhost:8080/Scrumming/service/itemBacklog/{itemID}";
		
		ResponseEntity<ItemBacklog> itemBacklog = restTemplate.getForEntity(url, ItemBacklog.class, itemID);

		return itemBacklog.getBody();
	}
}
