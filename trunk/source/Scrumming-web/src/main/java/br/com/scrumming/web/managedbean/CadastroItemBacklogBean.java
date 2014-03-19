package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.web.clientService.ItemBacklogClientService;

@ManagedBean
@ViewScoped
public class CadastroItemBacklogBean extends AbstractBean {

    private List<ItemBacklog> itens;
    private ItemBacklog itemBacklog;
    private ItemBacklogClientService clienteService;

    @Override
    public void inicializar() {
        itens = new ArrayList<ItemBacklog>();
        clienteService = new ItemBacklogClientService();
        itemBacklog = new ItemBacklog();
    }
    
	public String salvarItemBacklog() {
    	clienteService.salvarItemBacklog(itemBacklog);
    	return "";
    }

    public String excluir() {
       	clienteService.cancelarItemBacklog(itemBacklog);
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

    
}