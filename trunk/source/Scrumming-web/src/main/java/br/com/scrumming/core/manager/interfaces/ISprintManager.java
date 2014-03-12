package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;

public interface ISprintManager extends IManager<Sprint, Integer> {

    String salvarSprint(SprintDTO sprintDTO);
	List<Sprint> consultarPorProjeto(Integer projetoID);
    void fecharSprint(Sprint sprint);
    
}