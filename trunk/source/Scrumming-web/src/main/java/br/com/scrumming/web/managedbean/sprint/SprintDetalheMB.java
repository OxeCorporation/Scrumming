package br.com.scrumming.web.managedbean.sprint;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.clientService.SprintClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintDetalheMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	private List<ItemBacklog> itens;
	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private SprintClientService clienteService;
	@FlashScoped
	private Tarefa tarefa;
	@FlashScoped
	private Tarefa tarefaSelecionada;
	private TarefaClientService tarefaClientService;
		
	@Override
	public void inicializar() {
		tarefa = new Tarefa();
		tarefaSelecionada = new Tarefa();
		clienteService = new SprintClientService();
		tarefaClientService = new TarefaClientService();
		itens = clienteService.consultarSprintBacklog(sprintSelecionada.getCodigo());
	}
	
	public void preparaParaInserirTarefa() {
		tarefa = new Tarefa();
	}
	
	public void preparaParaAlterarTarefa() {
		tarefa = tarefaSelecionada;
	}
	
	private void atualizarListaDeTarefas() {
		if (itemSelecionado != null) {
			itemSelecionado.setTarefas(tarefaClientService.consultarTarefasPorItemBacklog(itemSelecionado.getCodigo()));
		}
	}
		
	public String salvarTarefa() {
		tarefaClientService.salvarTarefa(tarefa, itemSelecionado.getCodigo());
		limparObjetoTarefa();
		atualizarListaDeTarefas();
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
    	return "";
	}
	
	private void limparObjetoTarefa() {
		tarefa = null;		
	}

	public String removerTarefa(){
		tarefaClientService.removerTarefa(tarefaSelecionada);
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	/* Métodos para redirecionamento das páginas */
	public String sprintCadastroPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_CADASTRO_PAGE);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}
	
	public String itembacklogDetalhePage() {
    	return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
    }
	
	public String itemBacklogCadastroPage() {
		return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String sprintDetalhePage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_DETAIL_PAGE);
	}

	/* Getters anda Setters */
	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public ItemBacklog getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemBacklog itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public List<ItemBacklog> getItens() {
		return itens;
	}

	public void setItens(List<ItemBacklog> itens) {
		this.itens = itens;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}
}