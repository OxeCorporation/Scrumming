package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.repositorio.TeamRepositorio;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

@Service
public class TeamManager extends AbstractManager<Team, Integer> implements
		ITeamManager {

	/**
	 * Serial Version
	 */

	private static final long serialVersionUID = 1L;

	@Autowired
	private TeamRepositorio teamRepositorio;

	@Override
	public AbstractRepositorio<Team, Integer> getRepositorio() {
		return this.teamRepositorio;
	}

	@Override
	public List<Usuario> consultarUsuarioPorProjeto(Integer usuarioID) {
		return teamRepositorio.consultarUsuarioPorProjeto(usuarioID);
	}

	/* getters and setters */
	public TeamRepositorio getTeamRepositorio() {
		return teamRepositorio;
	}

	public void setTeamRepositorio(TeamRepositorio teamRepositorio) {
		this.teamRepositorio = teamRepositorio;
	}

}