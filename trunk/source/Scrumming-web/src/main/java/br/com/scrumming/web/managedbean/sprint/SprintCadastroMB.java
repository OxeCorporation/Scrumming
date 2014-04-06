package br.com.scrumming.web.managedbean.sprint;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintCadastroMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	private SprintClientService sprintClientService;
	private SprintDTO sprintDTO;
	
	@Override
	public void inicializar() {
		sprintDTO = new SprintDTO();
		sprintClientService = new SprintClientService();
	}
	
	/**
	 * Salva o sprintDTO
	 * @return
	 */
	public void salvarSprint() {
		sprintClientService.salvarSprint(sprintDTO);
		sprintPage();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	/*Métodos para redirecionamento das páginas*/
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	/*Getters anda Setters*/
	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public SprintDTO getSprintDTO() {
		return sprintDTO;
	}

	public void setSprintDTO(SprintDTO sprintDTO) {
		this.sprintDTO = sprintDTO;
	}
}
