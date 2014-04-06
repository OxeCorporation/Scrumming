package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Tarefa;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.clientService.TarefaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ItemBacklogDetalheMB extends AbstractBean {

	private List<ItemBacklog> itens;
	@FlashScoped
	private ItemBacklog itemBacklog;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private ItemBacklogClientService clienteService;
	@FlashScoped
	private Projeto projetoSelecionado;
	private TarefaClientService tarefaClientService;
	private Tarefa tarefa;
	@FlashScoped
	private Tarefa tarefaSelecionada;
	

	@Override
    public void inicializar() {
		tarefa = new Tarefa();
		tarefaClientService = new TarefaClientService();
		atualizarListaDeTarefas();
	}

	/* Métodos para redirecionamento das páginas */
	public String itemBacklogCadastroPage() {
		return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}
	
	public String itemBacklogDetalhePage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String tarefaCadastroPage() {
		return redirecionar(PaginasUtil.Tarefa.SAVE_PAGE);
	}

	
	/* Funções específicas da tela */
	private void atualizarListaDeTarefas() {
		itemSelecionado.setTarefas(tarefaClientService.consultarTarefasPorItemBacklog(itemSelecionado.getCodigo()));
	}
	
	public void salvarTarefa() {
		tarefaClientService.salvarTarefa(tarefa, itemSelecionado.getCodigo());
		atualizarListaDeTarefas();
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	public String removerTarefa(){
		tarefaClientService.removerTarefa(tarefaSelecionada);
		atualizarListaDeTarefas();
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	public String salvarItemBacklog() {
		clienteService.salvarItemBacklog(itemBacklog);
		return "";
	}

	public String cancelarItemBacklog() {
		clienteService.cancelarItemBacklog(itemBacklog);
		return "";
	}

	public String consultarPorProjeto(Integer projetoID) {
		// criado para teste
		itens = clienteService.consultarItemPorProjeto(projetoID);
		return "";
	}

	public String consultarItemPorID() {
		clienteService.consultarItemPorID(itemBacklog.getChave());
		return "";
	}

	
	/* getters and setters */
	public List<ItemBacklog> getItens() {
		return itens;
	}

	public void setItens(List<ItemBacklog> itens) {
		this.itens = itens;
	}

	public ItemBacklog getItemBacklog() {
		return itemBacklog;
	}

	public void setItemBacklog(ItemBacklog itemBacklog) {
		this.itemBacklog = itemBacklog;
	}

	public ItemBacklog getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemBacklog itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
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
