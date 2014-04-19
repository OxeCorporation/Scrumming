package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.TeamChave;
import br.com.scrumming.domain.Usuario;

public interface ITeamManager extends IManager<Team, TeamChave> {
	
	public void associarTeamProjeto(List<Team> team);
	public void desassociarUsuarioDoTeamProjeto(Projeto projetoPersistido, List<Team> team);
	List<Usuario> consultarUsuarioPorProjeto(Integer projetoID);
	public List<Usuario> consultarUsuarioPorEmpresaForaDoProjeto(Integer projetoID, Integer empresaID);
	public List<Team> consultaTeamPorProjeto(Integer projetoID);
}
