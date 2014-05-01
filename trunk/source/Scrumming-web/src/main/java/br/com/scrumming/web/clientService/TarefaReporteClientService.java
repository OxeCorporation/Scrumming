package br.com.scrumming.web.clientService;

import br.com.scrumming.domain.TarefaReporte;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TarefaReporteClientService extends AbstractClientService {
	
	public void reportarHora(TarefaReporte tarefaReporte) {		
		getRestTemplate().postForObject(
				getURIService(ConstantesService.TarefaReporte.URL_REPORTAR_HORA),
				tarefaReporte, void.class, tarefaReporte.getTarefa().getCodigo(), 
				tarefaReporte.getUsuario().getCodigo());
	}	
	
}
