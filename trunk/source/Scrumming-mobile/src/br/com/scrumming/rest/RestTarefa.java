package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.infra.RestFactory;

public class RestTarefa extends RestFactory {
	
public static List<Tarefa> retornarTarefas(Integer itemBacklogID){
		
//		String domain = "scrumming-agilscrum.rhcloud.com";
		String domain = "192.168.0.101:8080";
		
		final String url = "http://"+domain+"/Scrumming/service/tarefa/list/{itemBacklogID}";
		Tarefa[] tarefas = RestFactory.getRestTemplate().getForObject(url, Tarefa[].class, itemBacklogID);
		return Arrays.asList(tarefas);
	}
}