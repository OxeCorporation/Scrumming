package br.com.scrumming.core.manager.interfaces;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.TarefaFavoritaChave;

public interface ITarefaFavoritaManager extends IManager<TarefaFavorita, TarefaFavoritaChave>{
	
	void favoritarTarefa(TarefaFavorita tarefaFavorita);
}
