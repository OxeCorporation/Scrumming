package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@ViewScoped
public class SprintBean extends AbstractBean {
	
	private Sprint sprint;
	private List<Sprint> sprintsDoProjeto;
	private List<ItemBacklog> sprintBacklog;
	private List<ItemBacklog> availableBacklog;
	private SprintDTO sprintDTO;
	private SprintClientService sprintClientService;
	
	@Override
	public void inicializar() {
		sprintClientService = new SprintClientService();
	}
	
	public SprintBean() {
		inicializar();
		// TODO: Daniel terá que trazer o ID do projeto selecionado na lista de projetos para enviar como parametro.
		consultarSprintsPorProjeto(new Integer(1));
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
	 * Traz a lista de todas as Sprints do projeto.
	 * @param projetoID
	 * @return
	 */
	public String consultarSprintsPorProjeto(Integer projetoID) {
		sprintsDoProjeto = sprintClientService.consultarSprintsPorProjeto(projetoID);
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
	 * Consulta a lista dos items da sprint e suas tarefas.
	 * @param sprintID
	 * @return
	 */
	public String consultarItemTarefas(Integer sprintID) {
		sprintClientService.consultarItemsAndTasksDTO(sprintID);
		return "";
	}
	
	/**
	 * Fecha uma Sprint.
	 * @param sprintID
	 * @return
	 */
	public String fecharSprint(Integer sprintID) {
		sprintClientService.fecharSprint(sprintID);
		return "";
	}
	
	public String sprintDetailPage() {
    	return redirecionar(PaginasUtil.Sprint.SPRINT_DETAIL_PAGE);
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
}