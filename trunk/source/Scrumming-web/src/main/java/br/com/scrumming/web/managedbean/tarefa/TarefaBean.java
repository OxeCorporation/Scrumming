package br.com.scrumming.web.managedbean.tarefa;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class TarefaBean extends AbstractBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private Tarefa tarefa;
	private TarefaClientService tarefaClientService;
	private List<Tarefa> tarefasDoItem;
	
	@Override
    public void inicializar() {
		tarefa = new Tarefa();
		tarefaClientService = new TarefaClientService();
	}

	public void consultarTarefasPorItemBacklog(Integer itemBacklogID) {
		setTarefasDoItem(tarefaClientService.consultarTarefasPorItemBacklog(itemBacklogID));
	}
	
	
	
	
	
	/* getters and setters */
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<Tarefa> getTarefasDoItem() {
		return tarefasDoItem;
	}

	public void setTarefasDoItem(List<Tarefa> tarefasDoItem) {
		this.tarefasDoItem = tarefasDoItem;
	}
}
