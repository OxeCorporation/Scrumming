package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestSprint extends RestFactory {

	public static List<Sprint> retornarSprints(Projeto projeto){
		
		Integer projetoID = projeto.getCodigo();
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/sprint/list/{projetoId}";
		Sprint[] sprints = RestFactory.getRestTemplate().getForObject(url, Sprint[].class, projetoID);
		return Arrays.asList(sprints);
	}
}
