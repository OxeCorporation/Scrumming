package br.com.scrumming.core.manager.implementations;

import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.Projeto;

@Service
public class ProjetoManager extends AbstractManager<Projeto, Integer> implements IProjetoManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Override
    public AbstractRepositorio<Projeto, Integer> getRepositorio() {
        return this.projetoRepositorio;
    }

    @Override
    public List<Projeto> consultarPorNome(String nome) {
        return projetoRepositorio.consultarPorNome(nome);
    }
    
    @Override
	public List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim) {
		return projetoRepositorio.consultarPorPeriodo(dataInicio, dataFim);
	}

    /* getters and setters */
    public ProjetoRepositorio getSprintRepositorio() {
        return projetoRepositorio;
    }

    public void setSprintRepositorio(ProjetoRepositorio sprintRepositorio) {
        this.projetoRepositorio = sprintRepositorio;
    }
}