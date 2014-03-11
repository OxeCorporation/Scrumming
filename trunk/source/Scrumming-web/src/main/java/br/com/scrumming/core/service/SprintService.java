package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(method = RequestMethod.POST, value = "/{sprint}/itemSprint/{itensBacklogSprint}/itemBacklog/{itensBacklogProduto}")
    public void salvar(@PathVariable Sprint sprint, @PathVariable List<ItemBacklog> itensBacklogSprint, @PathVariable List<ItemBacklog> itensBacklogProduto) {
        this.sprintManager.salvarSprint(sprint, itensBacklogSprint, itensBacklogProduto);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/{sprint}")
    public void salvarTeste1(@PathVariable Sprint sprint) {
    	System.err.println("opa");
        this.sprintManager.salvarSprintTeste1(sprint);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/item/{sprint}")
    public void salvarTeste1(@PathVariable List<ItemBacklog> itensBacklogSprint) {
        this.sprintManager.salvarSprintTeste2(itensBacklogSprint);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<Sprint> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return new ArrayList<Sprint>(sprintManager.consultarPorProjeto(projetoId));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{sprintID}")
    public Sprint ConsultarPorChave(Integer sprintID) {
    	return sprintManager.findByKey(sprintID);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{sprint}")
    public void fechar(@PathVariable Sprint sprint) {
    	sprintManager.fecharSprint(sprint);
    }

    /* getters and setters */
	public ISprintManager getSprintManager() {
		return sprintManager;
	}

	public void setSprintManager(ISprintManager sprintManager) {
		this.sprintManager = sprintManager;
	}
}