package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.repositorio.TeamRepositorio;
import br.com.scrumming.domain.Projeto;
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
	
	@Override
	public List<Team> consultaTeamPorProjeto(Integer projetoID){
		
		return teamRepositorio.consultaTeamPorProjeto(projetoID);
	}
	
	public void associarTeamProjeto(Projeto projetoPersistido, List<Team> team) {
		
		for (Team item : team) {
			
			Team teamProjeto = new Team();
			
			teamProjeto.setEmpresa(item.getEmpresa());
			teamProjeto.setProjeto(item.getProjeto());
			teamProjeto.setUsuario(item.getUsuario());
			teamProjeto.setPerfilUsuario(item.getPerfilUsuario());
			teamProjeto.setAtivo(true);
			
			insertOrUpdate(teamProjeto);
			
		}
	}


	/*@Override
	public List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID) {
		return null;// teamRepositorio.consultarUsuarioPorEmpresa(empresaID);
	}*/
	
/*	@Override
	public List<Usuario> addTimeProjeto(List<Usuario> usuarioEmpresa, Integer empresaID){
		usuarioEmpresa=consultarUsuarioPorEmpresa(empresaID);
		return teamRepositorio.consultarUsuarioPorProjeto(empresaID);
	}*/


	/* getters and setters */
	public TeamRepositorio getTeamRepositorio() {
		return teamRepositorio;
	}

	public void setTeamRepositorio(TeamRepositorio teamRepositorio) {
		this.teamRepositorio = teamRepositorio;
	}
}