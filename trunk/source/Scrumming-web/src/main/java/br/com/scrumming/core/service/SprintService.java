package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

@RestController
@RequestMapping("/sprint")
public class SprintService {

    @Autowired
    private ISprintManager sprintManager;

    @RequestMapping(method = RequestMethod.POST, value = "/{sprint}/item/{itensBacklog}")
    public void salvar(@PathVariable @Valid Sprint sprint, @PathVariable List<ItemBacklog> itensBacklog) {
        this.sprintManager.salvarSprint(sprint, itensBacklog);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<Sprint> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return new ArrayList<Sprint>(sprintManager.consultarPorProjeto(projetoId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{sprintID}")
    public Sprint ConsultarPorChave(Integer sprintID) {
    	return sprintManager.findByKey(sprintID);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{sprintID}")
    public void fechar(@PathVariable Integer sprintID) {
    	
    }

    /* getters and setters */
	public ISprintManager getSprintManager() {
		return sprintManager;
	}

	public void setSprintManager(ISprintManager sprintManager) {
		this.sprintManager = sprintManager;
	}
}