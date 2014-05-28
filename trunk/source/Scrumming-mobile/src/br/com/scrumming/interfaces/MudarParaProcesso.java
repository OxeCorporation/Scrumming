package br.com.scrumming.interfaces;

import br.com.scrumming.domain.TarefaReporte;

public interface MudarParaProcesso {

	void clicouTarefaIrProcesso(TarefaReporte tarefa);
	void clicouTarefaVoltarPlanejada(TarefaReporte tarefa);
	void clicouTarefaVoltarProcesso(TarefaReporte tarefa);
	void clicouTarefaIrImpedida(TarefaReporte tarefa);
	void clicouTarefaIrConcluida(TarefaReporte tarefa);
}
