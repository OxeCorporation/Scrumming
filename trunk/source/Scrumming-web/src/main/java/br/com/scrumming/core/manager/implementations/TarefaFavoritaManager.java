package br.com.scrumming.core.manager.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.ObjectNotFoundException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ITarefaFavoritaManager;
import br.com.scrumming.core.repositorio.TarefaFavoritaRepositorio;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.TarefaFavoritaChave;

@Service
public class TarefaFavoritaManager extends AbstractManager<TarefaFavorita, TarefaFavoritaChave> implements ITarefaFavoritaManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TarefaFavoritaRepositorio tarefaFavoritaRepositorio;

	@Override
    public AbstractRepositorio<TarefaFavorita, TarefaFavoritaChave> getRepositorio() {
        return this.getTarefaFavoritaRepositorio();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favoritarTarefa(TarefaFavorita tarefaFavorita) {
    	TarefaFavoritaChave tarefaFavoritaChave = new TarefaFavoritaChave(tarefaFavorita.getTarefa(), tarefaFavorita.getUsuario());    	
    	TarefaFavorita tarefaFavoritaCadastrada = new TarefaFavorita();
    	
    	try {
			tarefaFavoritaCadastrada = tarefaFavoritaRepositorio.findByKey(tarefaFavoritaChave);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}    	
    	
    	tarefaFavorita.setFavorita(!tarefaFavoritaCadastrada.isFavorita()); 
    	
    	insertOrUpdate(tarefaFavorita);
    } 
   
    
    /* getters and setters */
	public TarefaFavoritaRepositorio getTarefaFavoritaRepositorio() {
		return tarefaFavoritaRepositorio;
	}

	public void setTarefaFavoritaRepositorio(TarefaFavoritaRepositorio tarefaFavoritaRepositorio) {
		this.tarefaFavoritaRepositorio = tarefaFavoritaRepositorio;
	}

}
