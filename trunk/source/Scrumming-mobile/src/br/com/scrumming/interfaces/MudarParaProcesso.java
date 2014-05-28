package br.com.scrumming.interfaces;

import br.com.scrumming.domain.Tarefa;

public interface MudarParaProcesso {

	void clicouTarefaIrProcesso(Tarefa tarefa);
	void clicouTarefaVoltarPlanejada(Tarefa tarefa);
	void clicouTarefaVoltarProcesso(Tarefa tarefa);
	void clicouTarefaIrImpedida(Tarefa tarefa);
	void clicouTarefaIrConcluida(Tarefa tarefa);
}
