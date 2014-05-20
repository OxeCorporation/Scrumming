package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.domain.Tarefa;

@RestController
@RequestMapping("/sprint")
public class SprintService {

    @Autowired
    private ISprintManager sprintManager;
    
    @Autowired
    private ISprintBacklogManager sprintBacklogManager;
    
    @Autowired
    private IItemBacklogManager itemBacklogManager;
    
    /*POSTS*/
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String salvarSprint(@RequestBody SprintDTO sprintDTO) {
    	return this.sprintManager.salvarSprint(sprintDTO);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/close")
    public void fechar(@RequestBody Sprint sprint) {
    	sprintManager.fecharSprint(sprint);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/sprintBacklog/tarefas/{sprintID}")
    public List<Tarefa> consultarTarefasPorSprint(@PathVariable Integer sprintID){
    	return sprintBacklogManager.consultarTarefasPorSprint(sprintID);
    }
    /*GETS*/
    @RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<Sprint> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return new ArrayList<Sprint>(sprintManager.consultarPorProjeto(projetoId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/list/disponiveis/{projetoID}")
    public List<ItemBacklog> consultarItensDisponiveis(@PathVariable Integer projetoID) {
    	return new ArrayList<ItemBacklog>(itemBacklogManager.consultarItensDisponiveisPorProjeto(projetoID));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{sprintID}")
    public SprintDTO consultarSprintDTO(@PathVariable Integer sprintID) {
    	return sprintManager.consultarSprintDTO(sprintID);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/sprintBacklog/list/{sprintID}/{usuarioLogadoID}")
    public List<ItemBacklog> consultarSprintBacklog(@PathVariable Integer sprintID, @PathVariable Integer usuarioLogadoID) {
    	return sprintBacklogManager.consultarSprintBacklog(sprintID, usuarioLogadoID);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/totalDeHorasEstimadas/{sprintID}")
    public Long totalDeHorasEstimadasDaSprint(@PathVariable Integer sprintID) {
    	return sprintBacklogManager.totalDeHorasEstimadasDaSprint(sprintID);
    }

    /* getters and setters */
	public ISprintManager getSprintManager() {
		return sprintManager;
	}

	public void setSprintManager(ISprintManager sprintManager) {
		this.sprintManager = sprintManager;
	}
}