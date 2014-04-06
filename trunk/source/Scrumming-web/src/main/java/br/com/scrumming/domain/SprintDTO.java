package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int dias;
	
	private Sprint sprint;
	
	private List<ItemBacklog> sprintBacklog;
	
	private List<ItemBacklog> productBacklog;

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

	public List<ItemBacklog> getProductBacklog() {
		return productBacklog;
	}

	public void setProductBacklog(List<ItemBacklog> productBacklog) {
		this.productBacklog = productBacklog;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}	
}