package br.com.scrumming.core.manager.interfaces;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.TarefaReporte;

public interface ITarefaReporteManager extends IManager<TarefaReporte, Integer>{
	
	void reportarHora(TarefaReporte tarefaReporte, Integer sprintID);
}
