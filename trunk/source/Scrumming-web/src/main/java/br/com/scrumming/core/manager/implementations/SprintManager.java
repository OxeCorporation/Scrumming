package br.com.scrumming.core.manager.implementations;

import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.repositorio.SprintRepositorio;
import br.com.scrumming.domain.Sprint;

@Service
public class SprintManager extends AbstractManager<Sprint, Integer> implements ISprintManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private SprintRepositorio sprintRepositorio;

    @Override
    public AbstractRepositorio<Sprint, Integer> getRepositorio() {
        return this.sprintRepositorio;
    }

    @Override
    public List<Sprint> consultarPorNome(String nome) {
        return sprintRepositorio.consultarPorNome(nome);
    }
    
    @Override
	public List<Sprint> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim) {
		return sprintRepositorio.consultarPorPeriodo(dataInicio, dataFim);
	}

    /* getters and setters */
    public SprintRepositorio getSprintRepositorio() {
        return sprintRepositorio;
    }

    public void setSprintRepositorio(SprintRepositorio sprintRepositorio) {
        this.sprintRepositorio = sprintRepositorio;
    }
}