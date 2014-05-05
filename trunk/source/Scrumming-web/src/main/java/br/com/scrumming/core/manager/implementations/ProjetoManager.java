package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

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
	private ItemBacklogManager itemBacklogManager;
	private ISprintBacklogManager sprintBacklogManager;
	private ISprintManager sprintManager;
	private ITarefaManager tarefaManager;

	@Override
	public AbstractRepositorio<Projeto, Integer> getRepositorio() {
		return this.projetoRepositorio;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String salvarProjeto(ProjetoDTO projetoDTO) {
		//int chave = projetoDTO.getProjeto().getChave();

		if (projetoDTO.getProjeto().getChave() == null) {
			DateTime projetoINI = (new DateTime(projetoDTO.getDataInicio()));
			DateTime projetoFIM = (new DateTime(projetoDTO.getDataFim()));
			int dia = new DateTime().getDayOfMonth();
			int mes = new DateTime().getMonthOfYear();
			int ano = new DateTime().getYear();
			DateTime hoje = new DateTime(ano, mes, dia, 0, 0, 0);
			if (projetoFIM.equals(projetoINI)) {
				throw new NegocioException(
						ConstantesMensagem.MENSAGEM_ERRO_DATA_PROJETO_FIM_IGUAL);
			} else if (projetoFIM.isBefore(projetoINI.getMillis())) {
				throw new NegocioException(
						ConstantesMensagem.MENSAGEM_ERRO_DATA_PROJETO_FIM_MENOR);
			} else if (projetoINI.isBefore(hoje)) {
				throw new NegocioException(
						ConstantesMensagem.MENSAGEM_ERRO_DATA_PROJETO_INICIO);
			} else {

				Projeto projeto = projetoDTO.getProjeto();
				projeto.setDataInicio(new DateTime(projetoDTO.getDataInicio()));
				projeto.setDataFim(new DateTime(projetoDTO.getDataFim()));
				projeto.setDataCadastro(new DateTime());

				List<Team> usuarioTeam = preparaTeamParaAssossiacao(projetoDTO);

				// Persiste o objeto Projeto e retorna a chave.
				Integer projetoID = insertOrUpdate(projeto);

				if (projetoID != null) {
					// Busca o objeto persistido pela chave.
					// Projeto projetoPersistido = findByKey(projetoID);

					if (CollectionUtils.isNotEmpty(usuarioTeam)) {
						teamManage.associarTeamProjeto(usuarioTeam, projetoID);
					}
				}
			}
		} else {
			alterarProjeto(projetoDTO);
		}
		return "";
	}

	@Transactional(rollbackFor = Exception.class)
	public String alterarProjeto(ProjetoDTO projetoDTO) {
		String retorno = "";

		DateTime projetoINI = (new DateTime(projetoDTO.getDataInicio()));
		DateTime projetoFIM = (new DateTime(projetoDTO.getDataFim()));
		if (projetoFIM.equals(projetoINI)) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_DATA_PROJETO_FIM_IGUAL);
		} else if (projetoFIM.isBefore(projetoINI.getMillis())) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_DATA_PROJETO_FIM_MENOR);
		} else {

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
		}
		return retorno;
	}

	@Transactional(readOnly = true)
	public ProjetoDTO consultarProjetoDTO(Integer projetoID) {

		// Cria o DTO que será enviado à tela para exibição
		ProjetoDTO projetoDTO = new ProjetoDTO();

		Projeto projeto = new Projeto();
		projeto = (findByKey(projetoID));

		// Seta o Projeto
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
	public String concluirProjeto(Projeto projeto) {
		List<ItemBacklog> itemBacklogConsulta = itemBacklogManager
				.consultarPorProjeto(projeto.getCodigo());
		if (itemBacklogConsulta != null) {
			boolean fechadas = true;
			for (int i = 0; i < itemBacklogConsulta.size(); i++) {
				if (!itemBacklogConsulta.get(i).getSituacaoBacklog().name()
						.equals(SituacaoItemBacklogEnum.ENTREGUE)) {
					fechadas = false;
					break;
				}
			}
			if (fechadas) {
				projeto = findByKey(projeto.getCodigo());
				projeto.setSituacaoProjeto(SituacaoProjetoEnum.CONCLUIDO);
				projeto.setDataFim(DateTime.now());
				insertOrUpdate(projeto);
			} else {
				List<Sprint> sprintsProjeto = sprintManager
						.consultarPorProjeto(projeto.getCodigo());
				for (int i = 0; i < sprintsProjeto.size(); i++) {
					if (sprintsProjeto.get(i).getSituacaoSprint() != SituacaoSprintEnum.FECHADA) {
						cancelarSprints(sprintsProjeto.get(i));
					}
				}
			}
		} else {

			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_DUPLICIDADE_DAILY);
		}
		return "";
	}

	private void cancelarSprints(Sprint sprint) {
		List<ItemBacklog> itensBacklogDaSprint = sprintBacklogManager
				.consultarSprintBacklog(sprint.getCodigo());
		for (int j = 0; j < itensBacklogDaSprint.size(); j++) {
			cancelarTarefas(itensBacklogDaSprint.get(j));
		}
		sprint.setSituacaoSprint(SituacaoSprintEnum.FECHADA);
		sprintManager.insertOrUpdate(sprint);
	}

	private void cancelarTarefas(ItemBacklog itensBacklogDaSprint) {
		List<Tarefa> tarefasItemBacklog = tarefaManager
				.consultarPorItemBacklog(itensBacklogDaSprint.getCodigo());
		if (tarefasItemBacklog != null) {
			for (int i = 0; i < tarefasItemBacklog.size(); i++) {
				if (tarefasItemBacklog.get(i).getSituacao() != SituacaoTarefaEnum.FEITO) {
					Tarefa tarefa = new Tarefa();
					tarefa = tarefasItemBacklog.get(i);
					tarefa.setSituacao(SituacaoTarefaEnum.CANCELADO);
					tarefaManager.salvar(tarefa,
							itensBacklogDaSprint.getCodigo());
				}
			}
		}
	}

	@Override
	public List<Projeto> consultarPorNome(String nome) {
		return projetoRepositorio.consultarPorNome(nome);
	}

	@Override
	public List<Projeto> consultarPorEmpresa(Integer empresaID) {
		return projetoRepositorio.consultarTodosPorEmpresa(empresaID);
	}

	@Override
	public List<Projeto> consultarPorPeriodo(DateTime dataInicio,
			DateTime dataFim) {
		return projetoRepositorio.consultarPorPeriodo(dataInicio, dataFim);
	}

	@Override
	public List<Projeto> consultarAtivosPorEmpresa(Integer empresaID) {
		return projetoRepositorio.consultarAtivosPorEmpresa(empresaID);
	}

	@Override
	public List<Projeto> consultarConcluidosPorEmpresa(Integer empresaID) {
		return projetoRepositorio.consultarConcluidosPorEmpresa(empresaID);
	}

	/* getters and setters */
	public ProjetoRepositorio getProjetoRepositorio() {
		return projetoRepositorio;
	}

	public void setProjetoRepositorio(ProjetoRepositorio projetoRepositorio) {
		this.projetoRepositorio = projetoRepositorio;
	}

}