package br.com.scrumming.core.repositorio;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.scrumming.core.infra.AbstractRepositorioTest;
import br.com.scrumming.domain.Contato;
import br.com.scrumming.domain.ContatoUtil;

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
