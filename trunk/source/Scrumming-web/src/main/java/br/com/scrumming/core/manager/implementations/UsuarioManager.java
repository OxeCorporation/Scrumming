package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.UsuarioRepositorio;
import br.com.scrumming.domain.Usuario;

@Service
public class UsuarioManager extends AbstractManager<Usuario, Integer> implements IUsuarioManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public AbstractRepositorio<Usuario, Integer> getRepositorio() {
        return this.usuarioRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> consultarPorNome(String nome) {
        return usuarioRepositorio.consultarPorNome(nome);
    }

    /* getters and sertters */
    public UsuarioRepositorio getUsuarioRepositorio() {
        return usuarioRepositorio;
    }

    public void setUsuarioRepositorio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario consultarPorLoginSenha(String login, String senha) {
        return this.consultarPorLoginSenha(login, senha);
    }

}