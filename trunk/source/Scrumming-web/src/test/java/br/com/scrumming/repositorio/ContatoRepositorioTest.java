package br.com.scrumming.repositorio;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.repositorio.ContatoRepositorio;
import br.com.scrumming.domain.Contato;
import br.com.scrumming.domain.ContatoUtil;
import br.com.scrumming.infra.AbstractRepositorioTest;

public class ContatoRepositorioTest extends AbstractRepositorioTest {

    @Autowired
    private ContatoRepositorio contatoRepositorio;

    @Test
    public void verificarInsercao() {

        Contato contato = ContatoUtil.criarPadrao();

        save(contato);

        Collection<Contato> contatoConsulta =
                contatoRepositorio.consultarPorCampo("nome", "Esdras");

        Assert.assertTrue(contatoConsulta.contains(contato));
    }
}
