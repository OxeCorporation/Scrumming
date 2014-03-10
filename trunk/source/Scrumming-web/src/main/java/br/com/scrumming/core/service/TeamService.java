package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

@RestController
@RequestMapping("/team")
public class TeamService {

	@Autowired
	private ITeamManager teamManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{salvarTeam}")
	public void salvarTeam(@PathVariable @Valid Team team) {
		this.teamManager.insertOrUpdate(team);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list/{usuarioId}")
	public List<Usuario> consultarUsuarioPorProjeto(
			@PathVariable Integer usuarioID) {
		return new ArrayList<Usuario>(
				teamManager.consultarUsuarioPorProjeto(usuarioID));
	}

	/* getters and setters */
	public ITeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(ITeamManager teamManager) {
		this.teamManager = teamManager;
	}

}
