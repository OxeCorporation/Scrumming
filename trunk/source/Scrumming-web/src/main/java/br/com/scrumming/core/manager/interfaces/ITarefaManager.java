package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

public interface ITarefaManager extends IManager<Tarefa, Integer>{
	
	void salvar(Tarefa tarefa, Integer itemBacklogID);
	void remover(Tarefa tarefa) throws Exception;
	
	List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID);
	List<Tarefa> consultarPorItemBacklogIhSituacao(Integer itemBacklogID, SituacaoTarefaEnum situacao);
	List<Tarefa> consultarPorItemBacklogIhNotSituacao(Integer itemBacklogID, SituacaoTarefaEnum situacao);
	void atribuirPara(Tarefa tarefa, Integer itemBacklogID, Integer usuarioID);
	void atualizarStatusTarefa(Integer tarefaID, SituacaoTarefaEnum situacaoTarefaEnum, Integer usuarioLogadoID);
	List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID, Integer usuarioLogadoID);
	void validarDadosAntesDeAtualizarStatus(Tarefa tarefa, Integer usuarioLogadoID);
}
