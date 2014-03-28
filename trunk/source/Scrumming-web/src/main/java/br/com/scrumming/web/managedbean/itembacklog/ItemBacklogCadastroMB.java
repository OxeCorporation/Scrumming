package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.PaginasUtil.Projeto;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ItemBacklogCadastroMB extends AbstractBean {

	private List<ItemBacklog> itens;
	private ItemBacklog itemBacklog;
	@FlashScoped
	private ItemBacklog itemSelecionado;
	private ItemBacklogClientService clienteService;
	@FlashScoped
	private Projeto projetoSelecionado;

	@Override
	public void inicializar() {
		@SuppressWarnings("unused")
		int o = 1;
	}

	/* Métodos para redirecionamento das páginas */
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}

	/* Funções específicas da tela */
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

}