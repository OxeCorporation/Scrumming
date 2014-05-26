package br.com.scrumming.rest;

import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestTarefaReport extends RestFactory {
	
public static void retornarTarefaReport(TarefaReporte tarefaReporte, Integer sprintID, Integer itemID, Integer tarefaID){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa_reporte/{sprintID}/{itemID}/{tarefaID}";
		RestFactory.getRestTemplate().postForObject(url, tarefaReporte, void.class, sprintID,itemID,tarefaID);
		return;
	}
}