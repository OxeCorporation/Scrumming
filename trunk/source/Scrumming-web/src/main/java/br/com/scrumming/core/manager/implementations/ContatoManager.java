package br.com.scrumming.core.manager.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IContatoManager;
import br.com.scrumming.core.repositorio.ContatoRepositorio;
import br.com.scrumming.domain.Contato;

@Service
public class ContatoManager extends AbstractManager<Contato, Integer> implements IContatoManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ContatoRepositorio contatoRepositorio;

    @Override
    public AbstractRepositorio<Contato, Integer> getRepositorio() {
        return getContatoRepositorio();
    }

    public ContatoRepositorio getContatoRepositorio() {
        return contatoRepositorio;
    }

    public void setContatoRepositorio(ContatoRepositorio contatoRepositorio) {
        this.contatoRepositorio = contatoRepositorio;
    }
}