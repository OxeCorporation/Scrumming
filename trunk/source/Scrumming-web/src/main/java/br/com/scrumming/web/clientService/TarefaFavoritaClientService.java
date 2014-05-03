package br.com.scrumming.web.clientService;

import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TarefaFavoritaClientService extends AbstractClientService {
	
	public void favoritarTarefa(TarefaFavorita tarefaFavorita) {		
		getRestTemplate().postForObject(
				getURIService(ConstantesService.TarefaFavorita.URL_FAVORITAR_TAREFA),
				tarefaFavorita, void.class);
	}	
	
}
