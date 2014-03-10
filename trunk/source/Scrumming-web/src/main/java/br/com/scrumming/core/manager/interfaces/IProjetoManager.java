package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import org.joda.time.DateTime;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

public interface IProjetoManager extends IManager<Projeto, Integer> {

	void salvarProjeto(Projeto sprint, List<Team> Team, List<Usuario> usuario);
    List<Projeto> consultarPorNome(String nome);
    List<Projeto> consultarPorPeriodo(DateTime dataInicio, DateTime dataFim);
}