package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.domain.ItemsTasksDTO;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;

@RestController
@RequestMapping("/sprint")
public class SprintService {

    @Autowired
    private ISprintManager sprintManager;
    
    /*POSTS*/
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String salvarSprint(@RequestBody SprintDTO sprintDTO) {
    	return this.sprintManager.salvarSprint(sprintDTO);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/close")
    public void fechar(@PathVariable Integer sprintID) {
    	sprintManager.fecharSprint(sprintID);
    }
    
    /*GETS*/
    @RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<Sprint> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return new ArrayList<Sprint>(sprintManager.consultarPorProjeto(projetoId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{sprintID}")
    public SprintDTO consultarSprintDTO(@PathVariable Integer sprintID) {
    	return sprintManager.consultarSprintDTO(sprintID);
    }
    
    
    
    @RequestMapping(method = RequestMethod.GET, value = "/itemsTarefas")
    public List<ItemsTasksDTO> consultarItemsAndTasksDTO(Integer sprintID) {
    	return sprintManager.consultarItemsAndTasksDTO(sprintID);
    }

    /* getters and setters */
	public ISprintManager getSprintManager() {
		return sprintManager;
	}

	public void setSprintManager(ISprintManager sprintManager) {
		this.sprintManager = sprintManager;
	}
}