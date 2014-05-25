package br.com.scrumming.interfaces;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnTarefaReporteItem {

	void clicouNaTarefaReportItem(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, 
						Sprint sprint, Tarefa tarefa);
}
