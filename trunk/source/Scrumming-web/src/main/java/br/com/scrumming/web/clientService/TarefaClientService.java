package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TarefaClientService extends AbstractClientService {

	public void salvarTarefa(Tarefa tarefa, Integer itemBacklogID) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Tarefa.URL_SALVAR), tarefa,
				void.class, itemBacklogID);
	}

	public List<Tarefa> consultarTarefasPorItemBacklog(Integer itemBacklogID) {
		return Arrays
				.asList(getRestTemplate()
						.getForObject(
								getURIService(ConstantesService.Tarefa.URL_CONSULTAR_POR_ITEM_BACKLOG),
								Tarefa[].class, itemBacklogID));
	}
	
	public List<Tarefa> consultarPorItemBacklogIhUsuarioLogado(Integer itemBacklogID, Integer usuarioLogadoID) {
		return Arrays
				.asList(getRestTemplate()
						.getForObject(
								getURIService(ConstantesService.Tarefa.URL_CONSULTAR_POR_ITEM_BACKLOG_IH_USUARIO_LOGADO),
								Tarefa[].class, itemBacklogID, usuarioLogadoID));
	}

	public List<Tarefa> consultarTarefasPorItemBacklogIhSituacao(
			Integer itemBacklogID, SituacaoTarefaEnum situacao) {
		return Arrays
				.asList(getRestTemplate()
						.getForObject(
								getURIService(ConstantesService.Tarefa.URL_CONSULTAR_POR_ITEM_BACKLOG_IH_SITUACAO),
								Tarefa[].class, itemBacklogID, situacao));
	}

	public Tarefa consultarTarefa(Integer tarefaID) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Tarefa> tarefa = restTemplate.getForEntity(
				ConstantesService.Tarefa.URL_CONSULTAR, Tarefa.class, tarefaID);

		return tarefa.getBody();
	}

	public void removerTarefa(Tarefa tarefa) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Tarefa.URL_REMOVER), tarefa,
				void.class);
	}

	public void atribuirTarefaPara(Tarefa tarefa, Integer usuarioID) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Tarefa.URL_ATRIBUIR_PARA),
				tarefa, void.class, tarefa.getItemBacklog().getCodigo(),
				usuarioID);
	}

	public void inserirOuAtualizar(Tarefa tarefa) {
		postForObject(
				getURIService(ConstantesService.Tarefa.URI_INSERT_OR_UPDATE),
				tarefa, void.class);
	}

	public void atualizarStatus(Integer tarefaID,
			SituacaoTarefaEnum situacaoTarefaEnum, Integer usuarioLogadoID) {
		postForObject(
				getURIService(ConstantesService.Tarefa.URI_ATUALIZAR_TAREFA),
				HttpEntity.EMPTY, void.class, tarefaID, situacaoTarefaEnum, usuarioLogadoID);
	}
	
	public void validarDadosAntesDeAtualizarStatus(Tarefa tarefa, Integer usuarioLogadoID) {
		
		getRestTemplate().postForObject(getURIService(
										ConstantesService.Tarefa.URL_VALIDAR_DADOS_ANTES_DE_ATUALIZAR_STATUS),
										tarefa, void.class, usuarioLogadoID);
	}
}
