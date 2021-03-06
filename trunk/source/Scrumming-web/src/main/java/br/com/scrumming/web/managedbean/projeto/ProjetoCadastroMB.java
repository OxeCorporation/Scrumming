package br.com.scrumming.web.managedbean.projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
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
	private List<Usuario> usuariosNaLista;

	@SuppressWarnings("unused")
	private List<PerfilUsuarioEnum> todosPerfis;
	private SituacaoProjetoEnum situacao;
	@SuppressWarnings("unused")
	private List<SituacaoProjetoEnum> todasSituacoes;
	private Usuario usuarioSelecionado;
	private Team usuarioTeamSelecionado;

	@ManagedProperty(value = "#{sessaoMB.empresaSelecionada}")
	private Empresa empresa;
	@FlashScoped
	private String titulo;

	@FlashScoped
	private List<Usuario> usuariosList;

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
		projetoClientService = new ProjetoClientService();
	}

	@SuppressWarnings("static-access")
	public String salvarProjeto() {
			projetoDTO.getProjeto().setEmpresa(empresa);
			projetoDTO.getProjeto().setSituacaoProjeto(situacao.ATIVO);
			projetoDTO.setTimeProjeto(teamProjeto);
			projetoDTO.setUsuarioEmpresaNotTeam(usuarioEmpresaNotProjeto);
			projetoClientService.salvarProjeto(projetoDTO);
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return projetoPage();
	}

	public List<Usuario> getTarefas() {
		return usuariosList;
	}

	public void setTarefas(List<Usuario> tarefas) {
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

	public List<Usuario> getUsuariosNaLista() {
		return usuariosNaLista;
	}

	public void setUsuariosNaLista(List<Usuario> usuariosNaLista) {
		this.usuariosNaLista = usuariosNaLista;
	}

	public List<Usuario> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}
}