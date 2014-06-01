package br.com.scrumming.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TarefaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Tarefa tarefa;
	private Long totalDeHorasReportadas;
	
	public TarefaDTO() {
		tarefa = new Tarefa();
	}
	
	public Long getTotalDeHorasReportadas() {
		return totalDeHorasReportadas;
	}
	public void setTotalDeHorasReportadas(Long totalDeHorasReportadas) {
		this.totalDeHorasReportadas = totalDeHorasReportadas;
	}
	public Tarefa getTarefa() {
		return tarefa;
	}
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

}
