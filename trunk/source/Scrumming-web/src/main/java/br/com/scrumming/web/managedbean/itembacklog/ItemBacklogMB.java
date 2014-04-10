package br.com.scrumming.web.managedbean.itembacklog;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ItemBacklogMB extends AbstractBean {

    private List<ItemBacklog> itens;
    @FlashScoped
    private ItemBacklog itemBacklog;
    @FlashScoped
    private ItemBacklog itemSelecionado;
    private ItemBacklogClientService clienteService;
    @FlashScoped
    private Projeto projetoSelecionado;
    private List<ItemBacklog> listaDataGrid;
   
    @Override
    public void inicializar() {
    	itemBacklog= new ItemBacklog();
        clienteService = new ItemBacklogClientService();
        atualizarListaDeItens();
    }
    
    /*Funções específicas da tela*/
	public String salvarItemBacklog() {
		itemBacklog.setProjeto(projetoSelecionado);
		clienteService.salvarItemBacklog(itemBacklog);
		atualizarListaDeItens();
		return "";
    }

	public void atualizarListaDeItens(){
			itens= clienteService.consultarItemPorProjeto(projetoSelecionado.getCodigo());
	}
	
    public String cancelarItemBacklog() {
       	clienteService.cancelarItemBacklog(itemSelecionado);
       	atualizarListaDeItens();
        return "";
    }
    
    public void alterar(){
    	itemBacklog= itemSelecionado;
    }
    
    public String consultarItemPorID() {
    	clienteService.consultarItemPorID(itemBacklog.getChave());
    	return "";
    }
    
    /*Métodos para redirecionamento das páginas*/
    public String itemBacklogCadastroPage(){
    	return redirecionar(PaginasUtil.ItemBacklog.CADASTRAR_ITEM_BACKLOG);
    }
    
    public String itembacklogDetalhePage(){
    	return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
    }
    
    public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
    
    public String projetoPage() {
    	return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
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
}