package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Usuario;

@RestController
@RequestMapping("/projeto")
public class ProjetoService {

    @Autowired
    private IProjetoManager projetoManager;
    
	@Autowired
	private ITeamManager teamManager;


    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public void salvarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        this.projetoManager.salvarProjeto(projetoDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/projetodto/{projetoID}")
    public ProjetoDTO consultarProjetoDTO(@PathVariable Integer projetoID) {
    	return projetoManager.consultarProjetoDTO(projetoID);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{empresaID}")
    public List<Projeto> consultarPorEmpresa(@PathVariable Integer empresaID) {
    	return new ArrayList<Projeto>(projetoManager.consultarPorEmpresa(empresaID));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/list/ativos/{empresaID}")
    public List<Projeto> consultarAtivosPorEmpresa(@PathVariable Integer empresaID) {
    	return new ArrayList<Projeto>(projetoManager.consultarAtivosPorEmpresa(empresaID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/concluidos/{empresaID}")
    public List<Projeto> consultarConcluidosPorEmpresa(@PathVariable Integer empresaID) {
    	return new ArrayList<Projeto>(projetoManager.consultarConcluidosPorEmpresa(empresaID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listusuario/{projetoID}/{empresaID}")
	public List<Usuario> consultarUsuarioForaDoProjeto(
			@PathVariable Integer projetoID, @PathVariable Integer empresaID) {
		return new ArrayList<Usuario>(
				teamManager.consultarUsuarioPorEmpresaForaDoProjeto(projetoID, empresaID));
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

    @RequestMapping(method = RequestMethod.POST, value = "/concluir")
    public void concluirProjeto(@RequestBody Projeto projeto) {
        this.projetoManager.concluirProjeto(projeto);;
    }

    /* getters and setters */
    public IProjetoManager getProjetoManager() {
        return projetoManager;
    }

    public void setProjetoManager(IProjetoManager projetoManager) {
        this.projetoManager = projetoManager;
    }
}