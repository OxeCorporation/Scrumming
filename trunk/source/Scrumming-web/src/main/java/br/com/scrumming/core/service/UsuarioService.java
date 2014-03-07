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

import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.domain.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioService {

    @Autowired
    private IUsuarioManager usuarioManager;

    @RequestMapping(method = RequestMethod.GET)
    public List<Usuario> listarTodos() {
        return new ArrayList<Usuario>(usuarioManager.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{nome}")
    public List<Usuario> consultarPorNome(@PathVariable String nome) {

        List<Usuario> retorno;

        if (StringUtils.isNotBlank(nome)) {
            retorno = new ArrayList<Usuario>(usuarioManager.consultarPorNome(nome));
        } else {
            retorno = new ArrayList<Usuario>(usuarioManager.findAll());
        }

        return retorno;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{usuario}")
    public void salvar(@PathVariable @Valid Usuario usuario) {
        this.usuarioManager.insertOrUpdate(usuario);
    }

    /* getters and setters */
    public IUsuarioManager getUsuarioManager() {
        return usuarioManager;
    }

    public void setUsuarioManager(IUsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }
}