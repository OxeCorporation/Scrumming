package br.com.scrumming.rest;

import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestTarefaFavorita {

	public static void favoritarTarefa(TarefaFavorita tarefaFavorita){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa_favorita/atualizar";
		RestFactory.getRestTemplate().postForObject(url, tarefaFavorita, void.class);
		return;
		}
}
