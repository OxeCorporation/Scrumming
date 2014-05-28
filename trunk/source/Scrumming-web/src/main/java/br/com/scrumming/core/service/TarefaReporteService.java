package br.com.scrumming.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ITarefaReporteManager;
import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;

@RestController
@RequestMapping("/tarefa_reporte")
public class TarefaReporteService {
	
	@Autowired
	private ITarefaReporteManager tarefaReporteManager;
	
	@RequestMapping(method = RequestMethod.POST, value ="/{sprintID}/{itemID}/{tarefaID}")
    public void reportarHora(@RequestBody TarefaReporte tarefaReporte, 
    		@PathVariable Integer sprintID, @PathVariable Integer itemID, 
    		@PathVariable Integer tarefaID) {
		tarefaReporteManager.reportarHora(tarefaReporte, sprintID, itemID, tarefaID);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/{itemID}")
	public List<TarefaDTO> totalDeHorasReportadasNasTarefasDoItem(@PathVariable Integer itemID) {
    	return tarefaReporteManager.totalDeHorasReportadasNasTarefasDoItem(itemID);
    }

	/* getters and setters */
	public ITarefaReporteManager getTarefaManager() {
		return tarefaReporteManager;
	}

	public void setTarefaManager(ITarefaReporteManager tarefaReporteManager) {
		this.tarefaReporteManager = tarefaReporteManager;
	}
}
