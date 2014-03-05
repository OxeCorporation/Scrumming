package br.com.scrumming.core.repositorio;

import org.springframework.stereotype.Repository;

import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.domain.Usuario;

@Repository
public class ProjetoRepositorio extends AbstractRepositorio<Usuario, Integer> {

}
