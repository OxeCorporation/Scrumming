package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
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
import br.com.scrumming.domain.TeamChave;
import br.com.scrumming.domain.Usuario;

@Service
public class TeamManager extends AbstractManager<Team, TeamChave> implements
		ITeamManager {

	/**
	 * Serial Version
	 */

	private static final long serialVersionUID = 1L;

	@Autowired
	private TeamRepositorio teamRepositorio;

	@Autowired
	private IUsuarioEmpresaManager iUsuarioEmpresaManager;


	@Override
	public AbstractRepositorio<Team, TeamChave> getRepositorio() {
		return this.teamRepositorio;
	}

	@Override
	public List<Usuario> consultarUsuarioPorProjeto(Integer projetoID) {
		return teamRepositorio.consultarUsuarioPorProjeto(projetoID);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Team> consultaTeamPorProjeto(Integer projetoID) {

		List<Team> teamProjeto = teamRepositorio
				.consultaTeamPorProjeto(projetoID);
		return teamProjeto;
	}
	
	public Team consultarTimeDoProjeto(int codigoProjeto, int codigoEmpresa, int codigoUsuario) {
		return teamRepositorio.consultarTimeDoProjeto(codigoProjeto, codigoEmpresa, codigoUsuario);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void associarTeamProjeto(List<Team> team, Integer projetoID) {
		List<Team> timeTodosTrueFalse = teamRepositorio
				.consultaTeamAtivosInativosPorProjeto(projetoID);
		if (timeTodosTrueFalse != null) {
			for (int i = 0; i < timeTodosTrueFalse.size(); i++) {
				timeTodosTrueFalse.get(i).setAtivo(false);
			}

			for (int i = 0; i < team.size(); i++) {
				boolean achei = false;
				int index=0;
				for (int j = 0; j < timeTodosTrueFalse.size(); j++) {
					if (team.get(i).getUsuario().getCodigo()==timeTodosTrueFalse.get(j).getUsuario().getCodigo()){
						achei=true;
						index=j;
					}
				}
				if (achei) {
					if (!timeTodosTrueFalse.get(index).isAtivo()) {
						Team item = timeTodosTrueFalse.get(index);
						item.setAtivo(true);
						insertOrUpdate(item);
					}
				} else {
					Team item = team.get(i);
					item.setAtivo(true);
					insertOrUpdate(item);
				}
			}
		} else {
			for (Team item : team) {
				item.setAtivo(true);
				insertOrUpdate(item);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> consultarUsuarioPorEmpresaForaDoProjeto(
			Integer projetoID, Integer empresaID) {
		List<Usuario> usuarioForaProjeto = new ArrayList<>();

		List<Usuario> usuarioEmpresa = new ArrayList<>(); 
			usuarioEmpresa = iUsuarioEmpresaManager
				.consultarUsuarioPorEmpresa(empresaID);
			
		List<Usuario> usuarioProjeto = new ArrayList<>();
				usuarioProjeto = teamRepositorio
				.consultarUsuarioPorProjeto(projetoID);

		for (int i = 0; i < usuarioEmpresa.size(); i++) {
			boolean achei = false;
			for (int j = 0; j < usuarioProjeto.size(); j++) {
				if (usuarioEmpresa.get(i).getCodigo() == usuarioProjeto.get(j)
						.getCodigo()) {
					achei = true;
				}
			}
			if (!achei) {
				usuarioForaProjeto.add(usuarioEmpresa.get(i));
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

	@Override
	public List<Team> consultaTeamAtivosInativosPorProjeto(Integer projetoID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Projeto> consultarProjetoPorUsuarioDaEmpresa(Integer usuarioID, Integer empresaID) {
		// TODO Auto-generated method stub
		return teamRepositorio.consultarProjetosPorUsuarioDaEmpresa(usuarioID, empresaID);
	}

}