package br.com.scrumming.interfaces;

import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;

public interface MudarParaProcesso {

	void clicouTarefaIrProcesso(TarefaDTO tarefaDTO);
	void clicouTarefaVoltarPlanejada(TarefaDTO tarefaDTO);
	void clicouTarefaVoltarProcesso(TarefaDTO tarefaDTO);
	void clicouTarefaIrImpedida(TarefaDTO tarefaDTO);
	void clicouTarefaIrConcluida(TarefaDTO tarefaDTO);
}
