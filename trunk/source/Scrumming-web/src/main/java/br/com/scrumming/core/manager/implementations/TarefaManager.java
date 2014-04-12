package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.repositorio.TarefaRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@Service
public class TarefaManager extends AbstractManager<Tarefa, Integer> implements ITarefaManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TarefaRepositorio tarefaRepositorio;
    @Autowired
    private IItemBacklogManager itemBacklogManager;

	@Override
    public AbstractRepositorio<Tarefa, Integer> getRepositorio() {
        return this.tarefaRepositorio;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void salvar(Tarefa tarefa, Integer itemBacklogManagerID) {
    	ItemBacklog itemBacklog = itemBacklogManager.findByKey(itemBacklogManagerID);
    	if (tarefa.getSituacao() == null)
    		tarefa.setSituacao(SituacaoTarefaEnum.PARA_FAZER);
    	tarefa.setItemBacklog(itemBacklog);
    	
    	insertOrUpdate(tarefa);
    }

    @Override
    public void remover(Tarefa tarefa) throws Exception {
        //validarDados(tarefa);
    	remove(tarefa);
    }

    private void validarDados(Tarefa tarefa) throws Exception {		
		if (tarefaRepositorio.existeReporteDeHorasNaTarefa(tarefa.getCodigo())) {
			throw new Exception("Impossível remover. Existe reporte de horas para esta tarefa.");
		}
		if (tarefaRepositorio.tarefaFoiFavoritada(tarefa.getCodigo())) {
			throw new Exception("Impossível remover. Essa tarefa foi favoritada por um usuário.");
		}
	}

	@Override
    public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID) {
        List<Tarefa> listaDeTarefas = tarefaRepositorio.consultarPorItemBacklog(itemBacklogID);
        List<Tarefa> novaListaDeTarefas = new ArrayList<>();
        for (Tarefa tarefa : listaDeTarefas) {
        	if (tarefa.getSituacao() == SituacaoTarefaEnum.PARA_FAZER){
        		tarefa.setSituacaoDescricao("Para fazer");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FAZENDO) {
        		tarefa.setSituacaoDescricao("Fazendo");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
        		tarefa.setSituacaoDescricao("Feito");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.CANCELADO) {
        		tarefa.setSituacaoDescricao("Cancelado");
        	} else if (tarefa.getSituacao() == SituacaoTarefaEnum.EM_IMPEDIMENTO) {
        		tarefa.setSituacaoDescricao("Em impedimento");
        	}
        	
        	novaListaDeTarefas.add(tarefa);
        }
		return novaListaDeTarefas;
    }
    
    
    /* getters and sertters */
    public TarefaRepositorio getTarefaRepositorio() {
		return tarefaRepositorio;
	}

	public void setTarefaRepositorio(TarefaRepositorio tarefaRepositorio) {
		this.tarefaRepositorio = tarefaRepositorio;
	}

}
