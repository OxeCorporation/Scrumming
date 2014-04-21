package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private Date dataRevisao;
	
	private Sprint sprint;
	
	private List<ItemBacklog> sprintBacklog;
	
	private List<ItemBacklog> productBacklog;

	public SprintDTO() {
		sprint = new Sprint();
		sprintBacklog = new ArrayList<ItemBacklog>();
		productBacklog = new ArrayList<ItemBacklog>();
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataRevisao() {
		return dataRevisao;
	}

	public void setDataRevisao(Date dataRevisao) {
		this.dataRevisao = dataRevisao;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}	
}