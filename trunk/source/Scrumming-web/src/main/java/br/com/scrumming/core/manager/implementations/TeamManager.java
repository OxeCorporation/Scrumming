package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
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
	
	@Autowired
	private IUsuarioEmpresaManager iUsuarioEmpresaManager;

	private List<Usuario> usuarioForaProjeto;
	
	@Override
	public AbstractRepositorio<Team, Integer> getRepositorio() {
		return this.teamRepositorio;
	}

	@Override
	public List<Usuario> consultarUsuarioPorProjeto(Integer projetoID) {
		return teamRepositorio.consultarUsuarioPorProjeto(projetoID);
	}
	
	@Override
	public List<Team> consultaTeamPorProjeto(Integer projetoID){
		
		return teamRepositorio.consultaTeamPorProjeto(projetoID);
	}
	
	public void associarTeamProjeto(Projeto projetoPersistido, List<Team> team) {
		
		for (Team item : team) {
			
			Team teamProjeto = new Team();
			
			teamProjeto.setEmpresa(projetoPersistido.getEmpresa());
			teamProjeto.setProjeto(projetoPersistido);
			teamProjeto.setUsuario(item.getUsuario());
			teamProjeto.setPerfilUsuario(item.getPerfilUsuario());
			teamProjeto.setAtivo(true);
			
			insertOrUpdate(teamProjeto);
			
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> consultarUsuarioPorEmpresaForaDoProjeto(Integer projetoID, Integer empresaID) {
		
		List<Usuario> usuarioEmpresa = iUsuarioEmpresaManager.consultarUsuarioPorEmpresa(empresaID);
		List<Usuario> usuarioProjeto = teamRepositorio.consultarUsuarioPorProjeto(projetoID);
		
		for (int i = 0; i <= usuarioEmpresa.size(); i++) {
			for (int j = 0; j < usuarioProjeto.size(); j++) {
				if (usuarioEmpresa.get(i).getCodigo()== usuarioProjeto.get(j).getCodigo()){
					continue;
				}else{
					usuarioForaProjeto.add(usuarioEmpresa.get(i));
				}
			}
		}
		return usuarioForaProjeto;
	}

	@Override
	public void desassociarUsuarioDoTeamProjeto(Projeto projetoPersistido,
			List<Team> team) {
		// TODO Auto-generated method stub
		
	}
	
	/* getters and setters */
	public TeamRepositorio getTeamRepositorio() {
		return teamRepositorio;
	}

	public void setTeamRepositorio(TeamRepositorio teamRepositorio) {
		this.teamRepositorio = teamRepositorio;
	}

}