package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.web.clientService.ItemBacklogClientService;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@ViewScoped
public class ItemBacklogBean extends AbstractBean {

    private List<ItemBacklog> itens;
    private ItemBacklog itemBacklog;
    private ItemBacklog itemSelecionado;
    private ItemBacklogClientService clienteService;

    @Override
    public void inicializar() {
        //itens = new ArrayList<ItemBacklog>();
        clienteService = new ItemBacklogClientService();
       // itemBacklog = new ItemBacklog();
        
    }
    
    public ItemBacklogBean(){
    	inicializar();
    	consultarPorProjeto(new Integer(1));
    }
    
    public String itembacklogDetailPage(){
    	return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_DETAIL_PAGE);
    }
    
	public String salvarItemBacklog() {
    	clienteService.salvarItemBacklog(itemBacklog);
    	return "";
    }

    public String cancelarItemBacklog() {
       	clienteService.cancelarItemBacklog(itemBacklog);
        return "";
    }
    
    public String consultarPorProjeto(Integer projetoID){
    	// criado para teste
    	itens= clienteService.consultarItemPorProjeto(projetoID);
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