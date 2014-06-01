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
import br.com.scrumming.domain.Projeto;
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

	@RequestMapping(method = RequestMethod.GET, value = "/list/{projetoID}")
	public List<Usuario> consultarUsuarioPorProjeto(@PathVariable Integer projetoID) {
		return new ArrayList<Usuario>(teamManager.consultarUsuarioPorProjeto(projetoID));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listProjetos/{usuarioID}/{empresaID}")
	public List<Projeto> consultarProjetoPorUsuarioDaEmpresa(@PathVariable Integer usuarioID, @PathVariable Integer empresaID) {
		return new ArrayList<Projeto>(teamManager.consultarProjetoPorUsuarioDaEmpresa(usuarioID, empresaID));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listusuario/{projetoId}{empresaID}")
	public List<Usuario> consultarUsuarioForaDoProjeto(
			@PathVariable Integer projetoID, @PathVariable Integer empresaID) {
		return new ArrayList<Usuario>(
				teamManager.consultarUsuarioPorEmpresaForaDoProjeto(projetoID, empresaID));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/timeProjeto/{codigoProjeto}/{codigoEmpresa}/{codigoUsuario}")
	public Team consultarTimeDoProjeto(@PathVariable Integer codigoProjeto, @PathVariable Integer codigoEmpresa, @PathVariable Integer codigoUsuario) {
		return teamManager.consultarTimeDoProjeto(codigoProjeto, codigoEmpresa, codigoUsuario);
	}

	/* getters and setters */
	public ITeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(ITeamManager teamManager) {
		this.teamManager = teamManager;
	}

}
