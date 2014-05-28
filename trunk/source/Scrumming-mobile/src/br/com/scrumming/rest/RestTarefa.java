package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestTarefa extends RestFactory {
	
public static List<TarefaReporte> retornarTarefa(Integer itembacklogID){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa_reporte/{itemID}";
		TarefaReporte[] tarefasReport = RestFactory.getRestTemplate().getForObject(url, TarefaReporte[].class, itembacklogID);
		return Arrays.asList(tarefasReport);
	}

public static void salvarOuAtualizarTarefa(Integer tarefaID, SituacaoTarefaEnum situacaoTarefaEnum, Integer usuarioEmpresaID){
	
	final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa/update/{tarefaID}"
															+ "/{situacaoTarefaEnum}/{usuarioLogadoID}";
	RestFactory.getRestTemplate().postForObject(url, HttpEntity.EMPTY, void.class, tarefaID, situacaoTarefaEnum, usuarioEmpresaID);
	return;
	}

public static void atribuirOuDesatribuirTarefa(Tarefa tarefa, Integer itembacklogID, Integer usuarioEmpresaID){
	
	final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/"
												+ "tarefa/atribuirpara/{itemBacklogID}/{usuarioID}";
	RestFactory.getRestTemplate().postForObject(url, tarefa, void.class, itembacklogID, usuarioEmpresaID);
	return;
	}

}