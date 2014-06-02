package br.com.scrumming.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TarefaHaReportarDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private TarefaReporte tarefaReporte;
	private int idSprint;
	private int idItembacklog;
	private int idTarefa;
	
	public TarefaHaReportarDTO() {
		tarefaReporte = new TarefaReporte();
	}
	
	public TarefaReporte getTarefaReporte() {
		return tarefaReporte;
	}

	public void setTarefaReporte(TarefaReporte tarefaReporte) {
		this.tarefaReporte = tarefaReporte;
	}

	public int getIdSprint() {
		return idSprint;
	}

	public void setIdSprint(int idSprint) {
		this.idSprint = idSprint;
	}

	public int getIdItembacklog() {
		return idItembacklog;
	}

	public void setIdItembacklog(int idItembacklog) {
		this.idItembacklog = idItembacklog;
	}

	public int getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(int idTarefa) {
		this.idTarefa = idTarefa;
	}

	public TarefaReporte getTarefa() {
		return tarefaReporte;
	}
	public void setTarefa(TarefaReporte tarefa) {
		this.tarefaReporte = tarefa;
	}

}
