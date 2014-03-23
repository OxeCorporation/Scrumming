package br.com.scrumming.core.service;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IContatoManager;
import br.com.scrumming.domain.Contato;

@RestController
@RequestMapping("/contato")
public class ContatoService {

    @Autowired
    private IContatoManager contatoManager;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Contato> findAll() {
        return this.contatoManager.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{nome}")
    public Collection<Contato> consultarContato(@PathVariable String nome) {

        if (StringUtils.isNotBlank(nome)) {
            return contatoManager.consultarPorCampo("nome", nome);
        } else {
            return this.contatoManager.findAll();
        }
    }

    /* gettters and setters */
    public IContatoManager getContatoManager() {
        return contatoManager;
    }

    public void setContatoManager(IContatoManager contatoManager) {
        this.contatoManager = contatoManager;
    }
}