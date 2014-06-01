package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.TarefaDTO;
import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TarefaReporteClientService extends AbstractClientService {
	
	public void reportarHora(TarefaReporte tarefaReporte, Integer sprintID, Integer itemID) {		
		getRestTemplate().postForObject(
				getURIService(ConstantesService.TarefaReporte.URL_REPORTAR_HORA),
				tarefaReporte, void.class, sprintID, itemID, tarefaReporte.getTarefa().getCodigo());
	}
	
	public List<TarefaDTO> totalDeHorasReportadasNasTarefasDoItem(Integer itemID) {
		TarefaDTO[] tarefaDTO = getRestTemplate().getForObject(getURIService(ConstantesService.
										TarefaReporte.URL_TOTAL_DE_HORAS_REPORTADAS_NAS_TAREFAS_DO_ITEM), 
										TarefaDTO[].class, itemID);
		
		return Arrays.asList(tarefaDTO);
	}
	
}
