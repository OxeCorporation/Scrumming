package br.com.scrumming.web.managedbean.projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
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
	private List<Usuario> usuarioEmpresa;
	@FlashScoped
	private List<Team> team;
	private List<Usuario> teamUsuario;
	private SituacaoProjetoEnum situacao;
	private List<SituacaoProjetoEnum> todasSituacoes;
	private Usuario usuarioSelecionado;
	private Usuario usuarioTeamSelecionado;
	
	@Override
	protected void inicializar() {
		@SuppressWarnings("unused")
		int o = 1;
		if (teamUsuario == null) {
			teamUsuario = new ArrayList<>();
		}
		
		if (usuarioEmpresa == null) {
			usuarioEmpresa = new ArrayList<>();
		}
		if (team == null) {
			team = new ArrayList<>();
		}
		if (projetoDTO == null) {
			projetoDTO = new ProjetoDTO();
		}

		projetoClientService = new ProjetoClientService();
	}
	
	public String salvarProjeto(){
		projetoDTO.getProjeto().setSituacaoProjeto(situacao);
		projetoDTO.setTimeProjeto(team);
		projetoDTO.setUsuarioEmpresaNotTeam(usuarioEmpresa);
		projetoClientService.salvarProjeto(projetoDTO);
		return "";
	}
	
	public String moveItemToTeam() {
		if (usuarioSelecionado != null) {
			List<Usuario> listToRemove = new LinkedList<>(usuarioEmpresa);
			Usuario itemToRemove = new Usuario();
			for (Usuario item : usuarioEmpresa) {
				if (item.getCodigo() == usuarioSelecionado.getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			usuarioEmpresa = listToRemove;
			teamUsuario.add(usuarioSelecionado);
			usuarioSelecionado = null;
		} else {
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeItemFromTeam() {
		if (usuarioTeamSelecionado != null) {
			List<Usuario> listToRemove = new LinkedList<>(teamUsuario);
			Usuario itemToRemove = new Usuario();
			for (Usuario item : teamUsuario) {
				if (item.getCodigo() == usuarioTeamSelecionado.getCodigo()) {
					itemToRemove = item;
				}
			}
			listToRemove.remove(itemToRemove);
			teamUsuario = listToRemove;
			usuarioEmpresa.add(usuarioTeamSelecionado);
			usuarioTeamSelecionado = null;
		} else {
			FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_SELECIONAR_ITEM_LISTA);
		}
		return "";
	}

	
	public List<Team> getTean() {
		return team;
	}

	public void setTean(List<Team> tean) {
		this.team = tean;
	}

	public List<Usuario> getTeamUsuario() {
		return teamUsuario;
	}

	public void setTeamUsuario(List<Usuario> teamUsuario) {
		this.teamUsuario = teamUsuario;
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

	public List<Usuario> getUsuarioEmpresa() {
		return usuarioEmpresa;
	}

	public void setUsuarioEmpresa(List<Usuario> usuarioEmpresa) {
		this.usuarioEmpresa = usuarioEmpresa;
	}

	public List<Team> getTeanUsuario() {
		return team;
	}

	public void setTeanUsuario(List<Team> teanUsuario) {
		this.team = teanUsuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Usuario getUsuarioTeamSelecionado() {
		return usuarioTeamSelecionado;
	}

	public void setUsuarioTeamSelecionado(Usuario usuarioTeamSelecionado) {
		this.usuarioTeamSelecionado = usuarioTeamSelecionado;
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
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

	public void setProjetoClientService(ProjetoClientService projetoClientService) {
		this.projetoClientService = projetoClientService;
	}
}