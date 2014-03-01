package br.com.scrumming.core.repositorio;

import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Contato;

@Repository
public class ContatoRepositorio extends AbstractRepositorio<Contato, Integer> {

}