package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@RestController
@RequestMapping("/tarefa")
public class TarefaService {
	
	@Autowired
	private ITarefaManager tarefaManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/save/{itemBacklogManagerID}")
    public void salvar(@RequestBody Tarefa tarefa,@PathVariable Integer itemBacklogManagerID){
		tarefaManager.salvar(tarefa, itemBacklogManagerID);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{tarefaID}")
    public Tarefa consultarPorChave(Integer tarefaID) {
		return tarefaManager.findByKey(tarefaID);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{itemBacklogID}")
	public List<Tarefa> consultarPorItemBacklog(@PathVariable Integer itemBacklogID) {
    	return new ArrayList<Tarefa>(tarefaManager.consultarPorItemBacklog(itemBacklogID));
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{itemBacklogID}/{situacao}")
	public List<Tarefa> consultarTarefasPorItemBacklogIhSituacao(@PathVariable Integer itemBacklogID, @PathVariable SituacaoTarefaEnum situacao) {
    	return new ArrayList<Tarefa>(tarefaManager.consultarPorItemBacklogIhSituacao(itemBacklogID, situacao));
    }
	
	@RequestMapping(method = RequestMethod.POST, value ="/remove/")
    public void remover(@RequestBody Tarefa tarefa) throws Exception {
    	tarefaManager.remover(tarefa);
    }
	
	@RequestMapping(method = RequestMethod.POST, value ="/atribuirpara/{itemBacklogID}/{usuarioID}")
    public void atribuirPara(@RequestBody Tarefa tarefa, @PathVariable Integer itemBacklogID, @PathVariable Integer usuarioID) {
    	tarefaManager.atribuirPara(tarefa, itemBacklogID, usuarioID);
    }

	/* getters and setters */
	public ITarefaManager getTarefaManager() {
		return tarefaManager;
	}

	public void setTarefaManager(ITarefaManager tarefaManager) {
		this.tarefaManager = tarefaManager;
	}
}
