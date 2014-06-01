package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestSprintBacklog extends RestFactory {
	
public static List<ItemBacklog> retornarSprintBacklog(Integer sprintID, Integer usuarioID){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/sprint/sprintBacklog/list/{sprintID}/{usuarioLogadoID}";
		ItemBacklog[] itensbacklog = RestFactory.getRestTemplate().getForObject(url, ItemBacklog[].class, sprintID, usuarioID);
		return Arrays.asList(itensbacklog);
	}

}
