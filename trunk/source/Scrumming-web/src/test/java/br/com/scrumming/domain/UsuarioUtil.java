package br.com.scrumming.domain;

import org.joda.time.DateTime;

public class UsuarioUtil {

    public static Usuario criarPadrao() {
        Usuario usuario = new Usuario();
        usuario.setNome("Esdras");
        usuario.setLogin("esdras");
        usuario.setDataCadastro(DateTime.now());
        return usuario;
    }

    public static Usuario criar(String nome) {
        Usuario usuarioPadrao = criarPadrao();
        usuarioPadrao.setNome(nome);
        usuarioPadrao.setLogin(nome.toLowerCase());
        return usuarioPadrao;
    }
}
