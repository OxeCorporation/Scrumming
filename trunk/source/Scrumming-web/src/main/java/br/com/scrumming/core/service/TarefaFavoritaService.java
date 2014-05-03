package br.com.scrumming.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ITarefaFavoritaManager;
import br.com.scrumming.domain.TarefaFavorita;

@RestController
@RequestMapping("/tarefa_favorita")
public class TarefaFavoritaService {
	
	@Autowired
	private ITarefaFavoritaManager tarefaFavoritaManager;
	
	@RequestMapping(method = RequestMethod.POST, value ="/atualizar")
    public void favoritarTarefa(@RequestBody TarefaFavorita tarefaFavorita) {
		getTarefaFavoritaManager().favoritarTarefa(tarefaFavorita);
    }

	/* getters and setters */
	public ITarefaFavoritaManager getTarefaFavoritaManager() {
		return tarefaFavoritaManager;
	}

	public void setTarefaFavoritaManager(ITarefaFavoritaManager tarefaFavoritaManager) {
		this.tarefaFavoritaManager = tarefaFavoritaManager;
	}
}
