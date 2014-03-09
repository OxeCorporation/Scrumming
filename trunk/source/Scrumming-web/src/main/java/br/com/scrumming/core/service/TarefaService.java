package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.domain.Tarefa;

@RestController
@RequestMapping("/tarefa")
public class TarefaService {
	
	@Autowired
	private ITarefaManager tarefaManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{tarefa}")
    public void salvar(@PathVariable @Valid Tarefa tarefa){
		tarefaManager.salvar(tarefa);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{tarefaID}")
    public Tarefa ConsultarPorChave(Integer tarefaID) {
		return tarefaManager.findByKey(tarefaID);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{itemBacklogID}")
	public List<Tarefa> consultarPorItemBacklog(@PathVariable Integer itemBacklogID) {
    	return new ArrayList<Tarefa>(tarefaManager.consultarPorItemBacklog(itemBacklogID));
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/{tarefaID}")
    public void remover(@PathVariable @Valid Tarefa tarefa) {
    	tarefaManager.remover(tarefa);
    }

	/* getters and setters */
	public ITarefaManager getTarefaManager() {
		return tarefaManager;
	}

	public void setTarefaManager(ITarefaManager tarefaManager) {
		this.tarefaManager = tarefaManager;
	}
}
