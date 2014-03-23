package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Usuario;

public interface IUsuarioManager extends IManager<Usuario, Integer> {

    List<Usuario> consultarPorNome(String nome);

    Usuario consultarPorLoginSenha(String login, String senha);
}
