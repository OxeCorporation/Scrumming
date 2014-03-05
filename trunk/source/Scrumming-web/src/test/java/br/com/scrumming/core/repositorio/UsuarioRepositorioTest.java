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

        List<Usuario> usuarios = usuarioRepositorio.consultarUsuarioPorNome("Esdras");

        Assert.assertTrue("TAMANHO DA LISTA INCORETO", usuarios.size() == 1);
        Assert.assertEquals("OS OBJETOS DEVERIAM SER IGUAIS", usuarios.get(0), usuario1);
    }

    @Test
    public void verificarConsulaPorNomeVarios() {

        Usuario usuario1 = UsuarioUtil.criar("Liabula");
        Usuario usuario2 = UsuarioUtil.criar("Natalia");
        Usuario usuario3 = UsuarioUtil.criar("Peliaculo");
        Usuario usuario4 = UsuarioUtil.criar("Wilson");

        save(usuario1, usuario2, usuario3, usuario4);

        List<Usuario> usuarios = usuarioRepositorio.consultarUsuarioPorNome("lia");

        Assert.assertTrue("TAMANHO DA LISTA INCORETO", usuarios.size() == 3);
        Assert.assertEquals("A ORDENAÇÂO DEVERIA ESTAR CORRETA", usuarios.get(0), usuario1);
    }
}
