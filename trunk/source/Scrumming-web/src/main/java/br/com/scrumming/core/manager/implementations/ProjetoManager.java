package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;

@Service
public class ProjetoManager extends AbstractManager<Projeto, Integer> implements
		IProjetoManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjetoRepositorio projetoRepositorio;

	@Autowired
	private TeamManager teamManage;
	private SprintManager sprintManage;

	@Override
	public AbstractRepositorio<Projeto, Integer> getRepositorio() {
		return this.projetoRepositorio;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String salvarProjeto(ProjetoDTO projetoDTO) {
		String retorno = "";
		Projeto projeto = projetoDTO.getProjeto();
		projeto.setDataInicio(new DateTime(projetoDTO.getDataInicio()));
		projeto.setDataFim(new DateTime(projetoDTO.getDataFim()));
		projeto.setDataCadastro(new DateTime());

		List<Team> usuarioTeam = preparaTeamParaAssossiacao(projetoDTO);

		// Persiste o objeto Projeto e retorna a chave.
		Integer projetoID = insertOrUpdate(projeto);

		if (projetoID != null) {
			retorno = "Registro foi salvo";
			// Busca o objeto persistido pela chave.
			// Projeto projetoPersistido = findByKey(projetoID);

			if (CollectionUtils.isNotEmpty(usuarioTeam)) {
				teamManage.associarTeamProjeto(usuarioTeam, projetoID);
			}
		}

		return retorno;
	}

	@Transactional(readOnly = true)
	public ProjetoDTO consultarProjetoDTO(Integer projetoID) {

		// Cria o DTO que será enviado à tela para exibição
		ProjetoDTO projetoDTO = new ProjetoDTO();
		// Seta o Projeto
		Projeto projeto = new Projeto();
		projeto = (findByKey(projetoID));

		projetoDTO.setProjeto(projeto);
		projetoDTO.setDataInicio(projeto.getDataInicio().toDate());
		projetoDTO.setDataFim(projeto.getDataFim().toDate());

		// Seta a lista de Times do projeto
		projetoDTO.setTimeProjeto(teamManage.consultaTeamPorProjeto(projetoID));
		projetoDTO.setUsuarioEmpresaNotTeam(teamManage
				.consultarUsuarioPorEmpresaForaDoProjeto(projetoID, projeto
						.getEmpresa().getCodigo()));
		return projetoDTO;
	}

	public List<Team> preparaTeamParaAssossiacao(ProjetoDTO projetoDTO) {
		List<Team> teamPreparado = new ArrayList<>();
		List<Team> usuarioTeam = projetoDTO.getTimeProjeto();
		for (Team team : usuarioTeam) {
			team.setEmpresa(projetoDTO.getProjeto().getEmpresa());
			team.setProjeto(projetoDTO.getProjeto());
			teamPreparado.add(team);
		}
		return teamPreparado;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void concluirProjeto(Projeto projeto) {
		List<Sprint> sprintConsulta = sprintManage.consultarPorProjeto(projeto
				.getCodigo());
		if (sprintConsulta != null) {
			boolean fechadas = true;
			for (int i = 0; i < sprintConsulta.size(); i++) {
				if (sprintConsulta.get(i).getSituacaoSprint().name()
						.equals(SituacaoSprintEnum.ABERTA)) {
					fechadas = false;
					break;
				}
			}
			if (fechadas) {
				projeto = findByKey(projeto.getCodigo());
				projeto.setSituacaoProjeto(SituacaoProjetoEnum.CONCLUIDO);
				projeto.setDataFim(DateTime.now());
				insertOrUpdate(projeto);
			}
		}
	}

	@Override
	public List<Projeto> consultarPorNome(String nome) {
		return projetoRepositorio.consultarPorNome(nome);
	}

	@Override
	public List<Projeto> consultarPorEmpresa(Integer empresaID) {
		return projetoRepositorio.consultarPorEmpresa(empresaID);
	}

	@Override
	public List<Projeto> consultarPorPeriodo(DateTime dataInicio,
			DateTime dataFim) {
		return projetoRepositorio.consultarPorPeriodo(dataInicio, dataFim);
	}

	/* getters and setters */
	public ProjetoRepositorio getProjetoRepositorio() {
		return projetoRepositorio;
	}

	public void setProjetoRepositorio(ProjetoRepositorio projetoRepositorio) {
		this.projetoRepositorio = projetoRepositorio;
	}
}