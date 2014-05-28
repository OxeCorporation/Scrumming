 package br.com.scrumming.core.manager.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.exceptions.ObjectNotFoundException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.ITarefaFavoritaManager;
import br.com.scrumming.core.repositorio.TarefaFavoritaRepositorio;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.TarefaFavorita;
import br.com.scrumming.domain.TarefaFavoritaChave;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

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
    	TarefaFavorita tarefaFavoritaCadastrada = new TarefaFavorita();
    	
    	try {
			tarefaFavoritaCadastrada = tarefaFavoritaRepositorio.findByKey(tarefaFavorita.getChave());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			tarefaFavoritaCadastrada.setTarefa(tarefaFavorita.getTarefa());
			tarefaFavoritaCadastrada.setUsuario(tarefaFavorita.getUsuario());
		}    	
    	    	
    	tarefaFavoritaCadastrada.setFavorita(!tarefaFavoritaCadastrada.isFavorita()); 
    	
    	validarAntesDeFavoritar(tarefaFavorita.getTarefa());
    	insertOrUpdate(tarefaFavoritaCadastrada);
    } 
    
    private void validarAntesDeFavoritar(Tarefa tarefa) {
		if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_ENCONTRA_SE_CONCLUIDA);
		}		
	}
    
    @Transactional(readOnly = true)
    public boolean tarefaFoiFavoritada(Tarefa tarefa, Usuario usuario){
    	TarefaFavoritaChave chave = new TarefaFavoritaChave(tarefa, usuario);    	
    	TarefaFavorita tarefaFavorita = findByKey(chave);
    	
    	return tarefaFavorita == null ? false : tarefaFavorita.isFavorita();    	
    }
   
    
    /* getters and setters */
	public TarefaFavoritaRepositorio getTarefaFavoritaRepositorio() {
		return tarefaFavoritaRepositorio;
	}

	public void setTarefaFavoritaRepositorio(TarefaFavoritaRepositorio tarefaFavoritaRepositorio) {
		this.tarefaFavoritaRepositorio = tarefaFavoritaRepositorio;
	}

}
