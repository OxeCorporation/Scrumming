package br.com.scrumming.domain;

import java.util.List;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

public class SprintDTO extends ObjetoPersistente<Integer> {
	
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private Sprint sprint;
	
	private List<ItemBacklog> sprintBacklog;
	
	private List<ItemBacklog> productBacklog;
	
	/*Getters and Setters*/
	@Override
	public Integer getChave() {
		return getCodigo();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

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
}
