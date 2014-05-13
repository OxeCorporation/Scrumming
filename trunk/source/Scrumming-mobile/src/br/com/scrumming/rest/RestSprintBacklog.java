package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.infra.RestFactory;

public class RestSprintBacklog extends RestFactory {
	
public static List<ItemBacklog> retornarSprintBacklog(Sprint sprint){
		
		String domain = "scrumming-agilscrum.rhcloud.com";
//		String domain = "192.168.0.101:8080";
		Integer sprintID = sprint.getCodigo();
		
		final String url = "http://"+domain+"/Scrumming/service/sprint/sprintBacklog/list/{sprintID}";
		ItemBacklog[] itensbacklog = RestFactory.getRestTemplate().getForObject(url, ItemBacklog[].class, sprintID);
		return Arrays.asList(itensbacklog);
	}

}
