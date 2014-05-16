package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.infra.RestFactory;

public class RestSprint extends RestFactory {

	public static List<Sprint> retornarSprints(Projeto projeto){
		
		//String domain = "scrumming-agilscrum.rhcloud.com";
		String domain = "192.168.0.101:8080";
		Integer projetoID = projeto.getCodigo();
		
		final String url = "http://"+domain+"/Scrumming/service/sprint/list/{projetoId}";
		Sprint[] sprints = RestFactory.getRestTemplate().getForObject(url, Sprint[].class, projetoID);
		return Arrays.asList(sprints);
	}
}
