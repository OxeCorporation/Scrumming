package br.com.scrumming.web.managedbean.sprint;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintMB extends AbstractBean {
	
	private static final long serialVersionUID = 1L;
	private List<Sprint> sprintsDoProjeto;
	private SprintClientService sprintClientService;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private SprintDTO sprintDTO;
	@FlashScoped
	private List<ItemBacklog> itensDisponiveis;
	@FlashScoped
	private List<ItemBacklog> sprintBacklog;
	
	@Override
	public void inicializar() {
		sprintClientService = new SprintClientService();
		sprintsDoProjeto = sprintClientService.consultarSprintsPorProjeto(projetoSelecionado.getCodigo());
	}
	
	/*Funções disponíveis para as telas da Sprint e SprintBacklog*/
	
	/**
	 * Consulta o DTO da Sprint.
	 * @param sprintID Código da Sprint.
	 * @return 
	 */
	public String consultarSprintDTO() {
		sprintDTO = sprintClientService.consultarSprintDTO(sprintSelecionada.getCodigo());
		sprintSelecionada = sprintDTO.getSprint();
		sprintBacklog = sprintDTO.getSprintBacklog();
		itensDisponiveis = sprintDTO.getProductBacklog();
		return sprintCadastroPage();
	}
	
	/**
	 * Fecha uma Sprint.
	 * @param sprintID
	 * @return
	 */
	public String fecharSprint() {
		sprintClientService.fecharSprint(sprintSelecionada);
		return sprintPage();
	}
	
	public String consultarItensDisponiveis() {
		itensDisponiveis = sprintClientService.consultarItensDisponiveis(projetoSelecionado.getCodigo());
		return sprintCadastroPage();
	}
	
	/*Métodos para redirecionamento das páginas*/
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String sprintDetailPage() {
    	return redirecionar(PaginasUtil.Sprint.SPRINT_DETAIL_PAGE);
    }
	
	public String sprintCadastroPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_CADASTRO_PAGE);
	}
	
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}
	
	public String projetoPage() {
		return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
	}
	
	/*Getters and Setters*/
	public List<ItemBacklog> getSprintBacklog() {
		return sprintBacklog;
	}

	public void setSprintBacklog(List<ItemBacklog> sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}

	public SprintDTO getSprintDTO() {
		return sprintDTO;
	}

	public void setSprintDTO(SprintDTO sprintDTO) {
		this.sprintDTO = sprintDTO;
	}

	public List<Sprint> getSprintsDoProjeto() {
		return sprintsDoProjeto;
	}

	public void setSprintsDoProjeto(List<Sprint> sprintsDoProjeto) {
		this.sprintsDoProjeto = sprintsDoProjeto;
	}

	public SprintClientService getSprintClientService() {
		return sprintClientService;
	}

	public void setSprintClientService(SprintClientService sprintClientService) {
		this.sprintClientService = sprintClientService;
	}
	
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

	public List<ItemBacklog> getItensDisponiveis() {
		return itensDisponiveis;
	}

	public void setItensDisponiveis(List<ItemBacklog> itensDisponiveis) {
		this.itensDisponiveis = itensDisponiveis;
	}
}