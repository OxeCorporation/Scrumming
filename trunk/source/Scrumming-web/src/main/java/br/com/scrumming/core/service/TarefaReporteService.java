package br.com.scrumming.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ITarefaReporteManager;
import br.com.scrumming.domain.TarefaReporte;

@RestController
@RequestMapping("/tarefa_reporte")
public class TarefaReporteService {
	
	@Autowired
	private ITarefaReporteManager tarefaReporteManager;
	
	@RequestMapping(method = RequestMethod.POST, value ="/{tarefaID}/{usuarioID}")
    public void reportarHora(@RequestBody TarefaReporte tarefaReporte, @PathVariable Integer tarefaID, @PathVariable Integer usuarioID) {
		tarefaReporteManager.reportarHora(tarefaReporte, tarefaID, usuarioID);
    }

	/* getters and setters */
	public ITarefaReporteManager getTarefaManager() {
		return tarefaReporteManager;
	}

	public void setTarefaManager(ITarefaReporteManager tarefaReporteManager) {
		this.tarefaReporteManager = tarefaReporteManager;
	}
}
