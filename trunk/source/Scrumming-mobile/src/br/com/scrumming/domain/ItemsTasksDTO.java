package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsTasksDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ItemBacklog item;
	private List<Tarefa> tarefas;	
	
	/*getters and setters*/
	public ItemBacklog getItem() {
		return item;
	}
	
	public void setItem(ItemBacklog item) {
		this.item = item;
	}
	
	public List<Tarefa> getTarefas() {
		return tarefas;
	}
	
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
}