package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.ItemsTasksDTO;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.SprintDTO;
import br.com.scrumming.web.clientService.SprintClientService;

@ManagedBean
@ViewScoped
public class SprintBean extends AbstractBean {
	
	private Sprint sprint;
	private List<ItemBacklog> sprintBacklog;
	private List<ItemBacklog> availableBacklog;
	private SprintDTO sprintDTO;
	private List<ItemsTasksDTO> itemsTasksDTO;
	private SprintClientService sprintClientService;
	
	@Override
	public void inicializar() {
		/*sprint = new Sprint();
		sprintBacklog = new ArrayList<>();
		availableBacklog = new ArrayList<>();
		sprintDTO = new SprintDTO();*/
		sprintClientService = new SprintClientService();
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
		sprintClientService.consultarSprintsPorProjeto(projetoID);
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
		itemsTasksDTO = sprintClientService.consultarItemsAndTasksDTO(sprintID);
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
}