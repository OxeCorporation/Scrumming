package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.domain.Projeto;

@RestController
@RequestMapping("/projeto")
public class ProjetoService {

    @Autowired
    private IProjetoManager projetoManager;

    @RequestMapping(method = RequestMethod.POST, value = "/{salvarProjeto}")
    public void salvarProjeto(@PathVariable @Valid Projeto projeto) {
        this.projetoManager.insertOrUpdate(projeto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{empresaID}")
    public List<Projeto> consultarPorEmpresa(@PathVariable Integer empresaID) {
    	return new ArrayList<Projeto>(projetoManager.consultarPorEmpresa(empresaID));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{nomeProjeto}")
    public List<Projeto> consultarPorNome(@PathVariable String nome) {

        List<Projeto> retorno;

        if (StringUtils.isNotBlank(nome)) {
            retorno = new ArrayList<Projeto>(projetoManager.consultarPorNome(nome));
        } else {
            retorno = new ArrayList<Projeto>(projetoManager.findAll());
        }

        return retorno;
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{excluiProjeto}")
    public void deletarProjeto(@PathVariable @Valid Projeto projeto) {
        this.projetoManager.remove(projeto);
    }


    /* getters and setters */
    public IProjetoManager getProjetoManager() {
        return projetoManager;
    }

    public void setProjetoManager(IProjetoManager projetoManager) {
        this.projetoManager = projetoManager;
    }
}