package br.com.scrumming.core.repositorio;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioUtil;

public class UsuarioRepositorioTest extends AbstractRepositorioTest {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    public void verificarConsultaPorNomeSomenteUm() {

        Usuario usuario1 = UsuarioUtil.criar("Esdras");
        Usuario usuario2 = UsuarioUtil.criar("Astrogildo");
        Usuario usuario3 = UsuarioUtil.criar("Gilmar");
        Usuario usuario4 = UsuarioUtil.criar("Wilson");

        save(usuario1, usuario2, usuario3, usuario4);

        List<Usuario> usuarios = usuarioRepositorio.consultarPorNome("Esdras");

        Assert.assertTrue(TAMANHO_DA_LISTA_INCORETO, usuarios.size() == 1);
        Assert.assertEquals(OBJETOS_DEVERIAM_SER_IGUAIS, usuarios.get(0), usuario1);
    }

    @Test
    public void verificarConsulaPorNomeVarios() {

        Usuario usuario1 = UsuarioUtil.criar("Liabula");
        Usuario usuario2 = UsuarioUtil.criar("Natalia");
        Usuario usuario3 = UsuarioUtil.criar("Peliaculo");
        Usuario usuario4 = UsuarioUtil.criar("Wilson");

        save(usuario1, usuario2, usuario3, usuario4);

        List<Usuario> usuarios = usuarioRepositorio.consultarPorNome("lia");

        Assert.assertTrue(TAMANHO_DA_LISTA_INCORETO, usuarios.size() == 3);
        Assert.assertEquals("A ORDENAÇÂO DEVERIA ESTAR CORRETA", usuarios.get(0), usuario1);
    }

    @Test
    public void verificarLoginSenha() {

        Usuario usuario1 = UsuarioUtil.criar("Lucio");
        Usuario usuario2 = UsuarioUtil.criar("Laisa");
        Usuario usuario3 = UsuarioUtil.criar("Pedro");

        save(usuario1, usuario2, usuario3);

        Usuario usuario =
                usuarioRepositorio.consultarPorLoginSenha(usuario1.getLogin(), usuario1.getSenha());

        Assert.assertEquals(OBJETOS_DEVERIAM_SER_IGUAIS, usuario1, usuario);
    }

    @Test
    public void verificarLoginSenhaERRO() {

        Usuario usuario1 = UsuarioUtil.criar("Lucio");
        Usuario usuario2 = UsuarioUtil.criar("Laisa");
        Usuario usuario3 = UsuarioUtil.criar("Pedro");

        save(usuario1, usuario2, usuario3);

        Usuario usuario = usuarioRepositorio.consultarPorLoginSenha("Lucio", usuario1.getSenha());

        Assert.assertNotEquals(OBJETOS_DEVERIAM_SER_DIFERENTES, usuario1, usuario);
    }
}