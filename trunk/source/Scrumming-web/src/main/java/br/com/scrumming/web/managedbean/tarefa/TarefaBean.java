package br.com.scrumming.web.managedbean.tarefa;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class TarefaBean extends AbstractBean {
	
	@FlashScoped
	private Tarefa tarefa;
	private TarefaClientService tarefaClientService;
	private List<Tarefa> tarefasDoItem;
	
	@Override
    public void inicializar() {
		tarefa = new Tarefa();
		tarefaClientService = new TarefaClientService();
	}
	
	public void salvar() {
		tarefaClientService.salvarTarefa(tarefa);
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	public void consultarTarefasPorItemBacklog(Integer itemBacklogID) {
		setTarefasDoItem(tarefaClientService.consultarTarefasPorItemBacklog(itemBacklogID));
	}
	
	public String tarefaCadastroPage() {
		return redirecionar(PaginasUtil.Tarefa.SAVE_PAGE);
	}
	
	public void removerTarefa(Tarefa tarefa){
		tarefaClientService.removerTarefa(tarefa);
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
