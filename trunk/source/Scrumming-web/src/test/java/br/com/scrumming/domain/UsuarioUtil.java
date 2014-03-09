package br.com.scrumming.domain;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class UsuarioUtil {

    public static Usuario criarPadrao() {
        String nome = "Esdras";
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setLogin(nome.toLowerCase());
        usuario.setSenha(StringUtils.reverse(nome.toLowerCase()));
        usuario.setDataCadastro(DateTime.now());
        return usuario;
    }

    public static Usuario criar(String nome) {
        Usuario usuarioPadrao = criarPadrao();
        usuarioPadrao.setNome(nome);
        usuarioPadrao.setLogin(nome.toLowerCase());
        usuarioPadrao.setSenha(StringUtils.reverse(nome.toLowerCase()));
        return usuarioPadrao;
    }
}
