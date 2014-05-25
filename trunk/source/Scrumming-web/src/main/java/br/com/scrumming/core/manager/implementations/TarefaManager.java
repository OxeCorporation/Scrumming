package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.infra.util.MensagemUtil;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ITarefaFavoritaManager;
import br.com.scrumming.core.manager.interfaces.ITarefaManager;
import br.com.scrumming.core.manager.interfaces.ITeamManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.TarefaReporteRepositorio;
import br.com.scrumming.core.repositorio.TarefaRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

@Service
public class TarefaManager extends AbstractManager<Tarefa, Integer> implements
		ITarefaManager {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TarefaRepositorio tarefaRepositorio;
	@Autowired
	private TarefaReporteRepositorio tarefaReporteRepositorio;
	@Autowired
	private IItemBacklogManager itemBacklogManager;
	@Autowired
	private IUsuarioManager usuarioManager;
	@Autowired
	private ITeamManager teamManager;
	@Autowired
	private ITarefaFavoritaManager tarefaFavoritaManager;

	@Override
	public AbstractRepositorio<Tarefa, Integer> getRepositorio() {
		return this.tarefaRepositorio;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void salvar(Tarefa tarefa, Integer itemBacklogID) {
		ItemBacklog itemBacklog = itemBacklogManager.findByKey(itemBacklogID);
		tarefa.setItemBacklog(itemBacklog);
		if (tarefa.getSituacao() == null)
			tarefa.setSituacao(SituacaoTarefaEnum.PARA_FAZER);

		validarDadosAntesDeSalvar(tarefa);
		insertOrUpdate(tarefa);
	}
	
	private void validarDadosAntesDeSalvar(Tarefa tarefa) {
		if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_ENCONTRA_SE_CONCLUIDA);
		}		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void atualizarStatusTarefa(Integer tarefaID,
			SituacaoTarefaEnum situacaoTarefaEnum, Integer usuarioLogadoID) {

		Tarefa tarefa = findByKey(tarefaID);		
		tarefa.setSituacao(situacaoTarefaEnum);
		
		validarDadosAntesDeAtualizarStatus(tarefa, usuarioLogadoID);
		insertOrUpdate(tarefa);
	}
	
	private void validarDadosAntesDeAtualizarStatus(Tarefa tarefa, Integer usuarioLogadoID) {
		if (tarefa.getUsuario() == null) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_SEM_USUARIO_ATRIBUIDO);
		}		
		
		if (tarefa.getUsuario().getCodigo() != usuarioLogadoID) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_APENAS_RESPONSAVEL_PODE_ALTERAR_STATUS);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void remover(Tarefa tarefa) {
		validarDadosAntesDeRemover(tarefa);
		remove(tarefa);
	}

	private void validarDadosAntesDeRemover(Tarefa tarefa) {
		if ((tarefa.getSituacao() != SituacaoTarefaEnum.PARA_FAZER)) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_SO_PODE_REMOVER_TAREFA_COM_SITUACAO_PARAFAZER);
		}
		if (tarefaReporteRepositorio.existeReporteDeHoras(tarefa.getCodigo())) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_IMPOSSIVEL_REMOVER_EXISTE_REPORTE_DE_HORAS_NA_TAREFA);
		}
		/*
		 * if (tarefaRepositorio.tarefaFoiFavoritada(tarefa.getCodigo())) {
		 * throw new NegocioException(ConstantesMensagem.
		 * MENSAGEM_ERRO_IMPOSSIVEL_REMOVER_TAREFA_FOI_FAVORITADA); }
		 */
	}

	@Override
	public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID) {
		List<Tarefa> listaDeTarefas = tarefaRepositorio
				.consultarPorItemBacklog(itemBacklogID);
		return preencherNovaListaDeTarefas(listaDeTarefas, 0);
	}
	
	@Override
	public List<Tarefa> consultarPorItemBacklog(Integer itemBacklogID, Integer usuarioLogadoID) {
		List<Tarefa> listaDeTarefas = tarefaRepositorio
				.consultarPorItemBacklog(itemBacklogID);
		return preencherNovaListaDeTarefas(listaDeTarefas, usuarioLogadoID);
	}

	private List<Tarefa> preencherNovaListaDeTarefas(List<Tarefa> listaDeTarefas, Integer usuarioLogadoID) {
		List<Tarefa> novaListaDeTarefas = new ArrayList<>();
		for (Tarefa tarefa : listaDeTarefas) {
			if (tarefa.getSituacao() == SituacaoTarefaEnum.PARA_FAZER) {
				String planejada = MensagemUtil.get(ConstantesMensagem.MENSAGEM_TAREFA_PLANEJADA);
				tarefa.setSituacaoDescricao(planejada);
				tarefa.setBackgroundColor("background-color: grey");
			} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FAZENDO) {
				String em_progresso = MensagemUtil.get(ConstantesMensagem.MENSAGEM_TAREFA_EM_PROGRESSO);
				tarefa.setSituacaoDescricao(em_progresso);
				tarefa.setBackgroundColor("background-color: blue");
			} else if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
				String concluida = MensagemUtil.get(ConstantesMensagem.MENSAGEM_TAREFA_COMCLUIDA);
				tarefa.setSituacaoDescricao(concluida);
				tarefa.setBackgroundColor("background-color: green");
			} else if (tarefa.getSituacao() == SituacaoTarefaEnum.CANCELADO) {
				String cancelada = MensagemUtil.get(ConstantesMensagem.MENSAGEM_TAREFA_CANCELADA);
				tarefa.setSituacaoDescricao(cancelada);
				tarefa.setBackgroundColor("background-color: red");
			} else if (tarefa.getSituacao() == SituacaoTarefaEnum.EM_IMPEDIMENTO) {
				String em_impedimento = MensagemUtil.get(ConstantesMensagem.MENSAGEM_TAREFA_EM_IMPEDIMENTO);
				tarefa.setSituacaoDescricao(em_impedimento);
				tarefa.setBackgroundColor("background-color: orange");
			}
			Usuario usuario = usuarioManager.findByKey(usuarioLogadoID);
			tarefa.setFoiFavoritada(tarefaFavoritaManager.tarefaFoiFavoritada(tarefa, usuario));

			novaListaDeTarefas.add(tarefa);
		}
		return novaListaDeTarefas;
	}

	@Override
	public List<Tarefa> consultarPorItemBacklogIhSituacao(
			Integer itemBacklogID, SituacaoTarefaEnum situacao) {
		List<Tarefa> listaDeTarefas = tarefaRepositorio
				.consultarPorItemBacklogIhSituacao(itemBacklogID, situacao);
		return preencherNovaListaDeTarefas(listaDeTarefas, 0);
	}

	@Override
	public List<Tarefa> consultarPorItemBacklogIhNotSituacao(
			Integer itemBacklogID, SituacaoTarefaEnum situacao) {
		return preencherNovaListaDeTarefas(tarefaRepositorio
				.consultarPorItemBacklogIhNotSituacao(itemBacklogID, situacao), 0);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void atribuirPara(Tarefa tarefa, Integer itemBacklogID,
			Integer usuarioID) {
		ItemBacklog itemBacklog = itemBacklogManager.findByKey(itemBacklogID);
		tarefa.setItemBacklog(itemBacklog);

		Usuario usuario = usuarioManager.findByKey(usuarioID);
		tarefa.setUsuario(usuario);

		if (usuario != null) {
			tarefa.setDataAtribuicao(new DateTime());
		} else {
			tarefa.setDataAtribuicao(null);
		}

		validarDadosAntesDeAtribuir(tarefa);
		insertOrUpdate(tarefa);
	}

	private void validarDadosAntesDeAtribuir(Tarefa tarefa) {
		if (tarefa.getSituacao() == SituacaoTarefaEnum.FEITO) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_ENCONTRA_SE_CONCLUIDA);
		}
		
		if (tarefa.getSituacao() == SituacaoTarefaEnum.CANCELADO) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_ENCONTRA_SE_CANCELADA);
		}
		
		if (tarefa.getSituacao() == SituacaoTarefaEnum.EM_IMPEDIMENTO) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_TAREFA_ENCONTRA_SE_IMPEDIDA);
		}
		
		List<Usuario> usuarios = teamManager.consultarUsuarioPorProjeto(tarefa
				.getItemBacklog().getProjeto().getCodigo());
		if (!usuarios.contains(tarefa.getUsuario())) {
			throw new NegocioException(
					ConstantesMensagem.MENSAGEM_ERRO_USUARIO_NAO_FAZ_PARTE_DO_TEAM);
		}		
	}

	/* getters and setters */
	public TarefaRepositorio getTarefaRepositorio() {
		return tarefaRepositorio;
	}

	public void setTarefaRepositorio(TarefaRepositorio tarefaRepositorio) {
		this.tarefaRepositorio = tarefaRepositorio;
	}

	public TarefaReporteRepositorio getTarefaReporteRepositorio() {
		return tarefaReporteRepositorio;
	}

	public void setTarefaReporteRepositorio(
			TarefaReporteRepositorio tarefaReporteRepositorio) {
		this.tarefaReporteRepositorio = tarefaReporteRepositorio;
	}

}
