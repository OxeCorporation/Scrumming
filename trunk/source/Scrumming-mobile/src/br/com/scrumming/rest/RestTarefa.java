package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestTarefa extends RestFactory {
	
public static List<Tarefa> retornarTarefa(Integer itembacklogID){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa/list/{itemBacklogID}";
		Tarefa[] tarefas = RestFactory.getRestTemplate().getForObject(url, Tarefa[].class, itembacklogID);
		return Arrays.asList(tarefas);
	}
}