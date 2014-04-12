package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;

@RestController
@RequestMapping("/itemBacklog")
public class ItemBacklogService {
	
	@Autowired
	private IItemBacklogManager itemBacklogManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/save")
    public void salvarItemBacklog(@RequestBody ItemBacklog itemBacklog){
		itemBacklogManager.salvarItemBacklog(itemBacklog);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cancel")
    public void cancelarItemBacklog(@RequestBody ItemBacklog item){
		itemBacklogManager.cancelarItem(item);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<ItemBacklog> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return itemBacklogManager.consultarPorProjeto(projetoId);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/{itemID}")
    public ItemBacklog consultarItemPorID(@PathVariable Integer itemId) {
    	return itemBacklogManager.consultarItemPorID(itemId);
    }

	@RequestMapping(method = RequestMethod.GET, value = "/list/{projetoIdSituacaoItem}")
	public List<ItemBacklog> consultarSituacaoDoItemPorProjeto(Integer projetoID){
		return new ArrayList<ItemBacklog>(itemBacklogManager.consultarSituacaoDoItemPorProjeto(projetoID));
	}
	/* getters and setters */
	public IItemBacklogManager getItemBacklogManager() {
		return itemBacklogManager;
	}

	public void setItemBacklogManager(IItemBacklogManager itemBacklogManager) {
		this.itemBacklogManager = itemBacklogManager;
	}
	
}
