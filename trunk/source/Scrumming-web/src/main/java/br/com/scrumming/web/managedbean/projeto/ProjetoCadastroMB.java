package br.com.scrumming.web.managedbean.projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioList;
import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.domain.enuns.SituacaoUsuarioList;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ProjetoCadastroMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private ProjetoDTO projetoDTO;
	private ProjetoClientService projetoClientService;
	@FlashScoped
	private List<Usuario> usuarioEmpresaNotProjeto;
	@FlashScoped
	private List<Team> teamProjeto;
	private List<UsuarioList> usuariosNaLista;

	@SuppressWarnings("unused")
	private List<PerfilUsuarioEnum> todosPerfis;
	private SituacaoProjetoEnum situacao;
	@SuppressWarnings("unused")
	private List<SituacaoProjetoEnum> todasSituacoes;
	private Usuario usuarioSelecionado;
	private Team usuarioTeamSelecionado;

	@ManagedProperty(value = "#{sessaoMB.empresaSelecionada}")
	private Empresa empresa;
	private Date dataInicio;
	private Date dataFim;
	@FlashScoped
	private String titulo;

	private DashboardModel dashboardModel;
	@FlashScoped
	private List<UsuarioList> usuariosList;
	private static final String PREFIX_TASK = "task_id_";

	@Override
	protected void inicializar() {
		if (usuarioEmpresaNotProjeto == null) {
			usuarioEmpresaNotProjeto = new ArrayList<>();
		}
		if (teamProjeto == null) {
			teamProjeto = new ArrayList<>();
		}
		if (usuariosNaLista == null) {
			usuariosNaLista = new ArrayList<>();
		}

		if (projetoDTO == null) {
			projetoDTO = new ProjetoDTO();
		}
		usuariosList = criarFaceTarefa();
		projetoClientService = new ProjetoClientService();
		

		init();
	}

	@SuppressWarnings("static-access")
	public String salvarProjeto() {
		for (UsuarioList usuarioList : usuariosList) {
			if (usuarioList.getSituacaoUsuarioList().equals(
					SituacaoUsuarioList.DO_PROJETO)) {
				Team team = new Team();
				team.setEmpresa(empresa);
				team.setUsuario(usuarioList.getUsuario());
				team.setPerfilUsuario(PerfilUsuarioEnum.TEAM);
				team.setProjeto(projetoSelecionado);
				team.setAtivo(true);
				teamProjeto.add(team);
			}
		}
		String retorno = "";
		projetoDTO.getProjeto().setEmpresa(empresa);
		projetoDTO.getProjeto().setSituacaoProjeto(situacao.ATIVO);
		projetoDTO.setTimeProjeto(teamProjeto);
		projetoDTO.setUsuarioEmpresaNotTeam(usuarioEmpresaNotProjeto);
		String msg = projetoClientService.salvarProjeto(projetoDTO);
		FacesMessageUtil
				.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		retorno = redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
		return retorno;
	}

	private void init() {
		dashboardModel = new DefaultDashboardModel();
		DashboardColumn timeProj = new DefaultDashboardColumn();
		DashboardColumn usuarioDisp = new DefaultDashboardColumn();

		dashboardModel.addColumn(timeProj);
		dashboardModel.addColumn(usuarioDisp);

		for (UsuarioList tarefa : usuariosNaLista) {
			DashboardColumn column = dashboardModel.getColumn(tarefa
					.getSituacaoUsuarioList().ordinal());
			column.addWidget(PREFIX_TASK + tarefa.getUsuario().getCodigo());
		}
	}

	private List<UsuarioList> criarFaceTarefa() {
		for (Usuario tarefa : usuarioEmpresaNotProjeto) {
			UsuarioList usuarioList = new UsuarioList();
			usuarioList.setUsuario(tarefa);
			usuarioList.setSituacaoUsuarioList(SituacaoUsuarioList.NOT_PROJETO);
			usuariosNaLista.add(usuarioList);

		}
		for (Team tarefa : teamProjeto) {
			UsuarioList usuarioList = new UsuarioList();
			usuarioList.setUsuario(tarefa.getUsuario());
			usuarioList.setSituacaoUsuarioList(SituacaoUsuarioList.DO_PROJETO);
			usuariosNaLista.add(usuarioList);

		}

		return usuariosNaLista;
	}

	public void handleReorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		int columnIndex = event.getColumnIndex();
		UsuarioList task = findTarefa(Integer.valueOf(widgetId
				.substring(PREFIX_TASK.length())));
		task.setSituacaoUsuarioList((SituacaoUsuarioList.values()[columnIndex]));
	}

	private UsuarioList findTarefa(Integer id) {
		for (UsuarioList tarefa : usuariosList) {
			if (tarefa.getUsuario().getCodigo().equals(id)) {
				return tarefa;
			}
		}
		return null;
	}

	public DashboardModel getDashboardModel() {
		return dashboardModel;
	}

	public void setDashboardModel(DashboardModel dashboardModel) {
		this.dashboardModel = dashboardModel;
	}

	public List<UsuarioList> getTarefas() {
		return usuariosList;
	}

	public void setTarefas(List<UsuarioList> tarefas) {
		this.usuariosList = tarefas;
	}

	public String moveItemToTeam() {
		if (usuarioSelecionado != null) {
			List<Usuario> listToRemove = new LinkedList<>(
					usuarioEmpresaNotProjeto);
			Usuario itemToRemove = new Usuario();
			for (Usuario item : usuarioEmpresaNotProjeto) {
				if (item.getCodigo() == usuarioSelecionado.getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			usuarioEmpresaNotProjeto = listToRemove;
			Team teamNaLista = new Team();
			teamNaLista.setUsuario(usuarioSelecionado);
			teamProjeto.add(teamNaLista);
			usuarioSelecionado = null;
		} else {
			FacesMessageUtil
					.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}

	/**
	 * 
	 * @return
	 */
	public String removeItemFromTeam() {
		if (usuarioTeamSelecionado != null) {
			List<Team> listToRemove = new LinkedList<>(teamProjeto);
			Team itemToRemove = new Team();
			for (Team item : teamProjeto) {
				if (item.getUsuario().getCodigo() == usuarioTeamSelecionado
						.getUsuario().getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			teamProjeto = listToRemove;
			Usuario usuario = itemToRemove.getUsuario();
			usuarioEmpresaNotProjeto.add(usuario);
			usuarioTeamSelecionado = null;
		} else {
			FacesMessageUtil
					.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}

	public List<Usuario> getUsuarioEmpresaNotProjeto() {
		return usuarioEmpresaNotProjeto;
	}

	public void setUsuarioEmpresaNotProjeto(
			List<Usuario> usuarioEmpresaNotProjeto) {
		this.usuarioEmpresaNotProjeto = usuarioEmpresaNotProjeto;
	}

	public List<Team> getTeamProjeto() {
		return teamProjeto;
	}

	public void setTeamProjeto(List<Team> teamProjeto) {
		this.teamProjeto = teamProjeto;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Team getUsuarioTeamSelecionado() {
		return usuarioTeamSelecionado;
	}

	public void setUsuarioTeamSelecionado(Team usuarioTeamSelecionado) {
		this.usuarioTeamSelecionado = usuarioTeamSelecionado;
	}

	public List<Usuario> getUsuarioEmpresa() {
		return usuarioEmpresaNotProjeto;
	}

	public void setUsuarioEmpresa(List<Usuario> usuarioEmpresa) {
		this.usuarioEmpresaNotProjeto = usuarioEmpresa;
	}

	public List<Team> getTeam() {
		return teamProjeto;
	}

	public void setTeam(List<Team> team) {
		this.teamProjeto = team;
	}

	public List<PerfilUsuarioEnum> getTodosPerfis() {
		return Arrays.asList(PerfilUsuarioEnum.values());
	}

	public void setTodosPerfis(List<PerfilUsuarioEnum> todosPerfis) {
		this.todosPerfis = todosPerfis;
	}

	public SituacaoProjetoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProjetoEnum situacao) {
		this.situacao = situacao;
	}

	public List<SituacaoProjetoEnum> getTodasSituacoes() {
		return Arrays.asList(SituacaoProjetoEnum.values());
	}

	public void setTodasSituacoes(List<SituacaoProjetoEnum> todasSituacoes) {
		this.todasSituacoes = todasSituacoes;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String projetoPage() {
		return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public ProjetoDTO getProjetoDTO() {
		return projetoDTO;
	}

	public void setProjetoDTO(ProjetoDTO projetoDTO) {
		this.projetoDTO = projetoDTO;
	}

	public ProjetoClientService getProjetoClientService() {
		return projetoClientService;
	}

	public void setProjetoClientService(
			ProjetoClientService projetoClientService) {
		this.projetoClientService = projetoClientService;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<UsuarioList> getUsuariosNaLista() {
		return usuariosNaLista;
	}

	public void setUsuariosNaLista(List<UsuarioList> usuariosNaLista) {
		this.usuariosNaLista = usuariosNaLista;
	}

	public List<UsuarioList> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(List<UsuarioList> usuariosList) {
		this.usuariosList = usuariosList;
	}

}