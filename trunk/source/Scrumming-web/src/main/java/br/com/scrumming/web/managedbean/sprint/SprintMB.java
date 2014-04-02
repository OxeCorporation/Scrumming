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
	
	private Sprint sprint;
	private List<Sprint> sprintsDoProjeto;
	private List<ItemBacklog> sprintBacklog;
	private List<ItemBacklog> availableBacklog;
	private SprintDTO sprintDTO;
	private SprintClientService sprintClientService;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private Sprint sprintSelecionada;
	
	@Override
	public void inicializar() {
		sprintClientService = new SprintClientService();
		sprintsDoProjeto = sprintClientService.consultarSprintsPorProjeto(projetoSelecionado.getCodigo());
	}
	
	/*Funções disponíveis para as telas da Sprint e SprintBacklog*/
	
	/**
	 * Salva o sprintDTO
	 * @return
	 */
	public String salvarSprint() {
		sprintClientService.salvarSprint(sprintDTO);
		return "";
	}
	
	/**
	 * Consulta o DTO da Sprint.
	 * @param sprintID Código da Sprint.
	 * @return 
	 */
	public String consultarSprintDTO(Integer sprintID) {
		sprintDTO = sprintClientService.consultarSprintDTO(sprintID);
		sprint = sprintDTO.getSprint();
		sprintBacklog = sprintDTO.getSprintBacklog();
		availableBacklog = sprintDTO.getProductBacklog();
		return "";
	}
	
	/**
	 * Fecha uma Sprint.
	 * @param sprintID
	 * @return
	 */
	public String fecharSprint() {
		sprintClientService.fecharSprint(sprintSelecionada.getChave());
		return "";
	}
	
	/*Métodos para redirecionamento das páginas*/
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
	
	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public List<ItemBacklog> getSprintBacklog() {
		return sprintBacklog;
	}

	public void setSprintBacklog(List<ItemBacklog> sprintBacklog) {
		this.sprintBacklog = sprintBacklog;
	}

	public List<ItemBacklog> getAvailableBacklog() {
		return availableBacklog;
	}

	public void setAvailableBacklog(List<ItemBacklog> availableBacklog) {
		this.availableBacklog = availableBacklog;
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
}
