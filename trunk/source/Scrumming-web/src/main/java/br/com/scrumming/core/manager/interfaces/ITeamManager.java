package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;

public interface ITeamManager extends IManager<Team, Integer> {
	
	public void associarTeamProjeto(Projeto projetoPersistido, List<Team> team);
	List<Usuario> consultarUsuarioPorProjeto(Integer usuarioID);
	public List<Usuario> consultarUsuarioPorEmpresaForaDoProjeto(Projeto projeto);
	public List<Team> consultaTeamPorProjeto(Integer projetoID);
}
