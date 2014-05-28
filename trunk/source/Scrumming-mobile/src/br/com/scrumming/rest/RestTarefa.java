package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestTarefa extends RestFactory {
	
public static List<TarefaDTO> retornarTarefa(Integer itembacklogID){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/tarefa/listDTO/{itemID}";
		TarefaDTO[] tarefasDTO = RestFactory.getRestTemplate().getForObject(url, TarefaDTO[].class, itembacklogID);
		return Arrays.asList(tarefasDTO);
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