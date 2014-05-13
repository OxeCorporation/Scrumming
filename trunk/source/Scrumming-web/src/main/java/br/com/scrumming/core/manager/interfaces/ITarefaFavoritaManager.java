package br.com.scrumming.core.manager.interfaces;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.TarefaFavoritaChave;
import br.com.scrumming.domain.Usuario;

public interface ITarefaFavoritaManager extends IManager<TarefaFavorita, TarefaFavoritaChave>{
	
	void favoritarTarefa(TarefaFavorita tarefaFavorita);

	boolean tarefaFoiFavoritada(Tarefa tarefa, Usuario usuario);
}
