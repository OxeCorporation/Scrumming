package br.com.scrumming.web.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.clientService.TarefaClientService;

@ManagedBean
@ViewScoped
public class TarefaBean extends AbstractBean {
	
	private Tarefa tarefa;
	private TarefaClientService tarefaClientService;
	
	@Override
    public void inicializar() {
		tarefa = new Tarefa();
		tarefaClientService = new TarefaClientService();
	}
	
	public void salvar() {
		tarefaClientService.salvarTarefa(tarefa);
	}
	
	/* getters and setters */
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
}
