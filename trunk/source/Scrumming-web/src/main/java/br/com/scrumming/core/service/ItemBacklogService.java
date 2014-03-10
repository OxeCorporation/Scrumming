package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.domain.ItemBacklog;

@RestController
@RequestMapping("/itemBacklog")
public class ItemBacklogService {
	
	@Autowired
	private IItemBacklogManager itemBacklogManager;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{itemBacklog}")
    public void salvarItemBacklog(@PathVariable @Valid ItemBacklog itemBacklog){
		itemBacklogManager.salvarItemBlacklog(itemBacklog);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{item}")
    public void cancelarItemBacklog(@PathVariable @Valid ItemBacklog item){
		itemBacklogManager.cancelarItem(item);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/{projetoId}")
    public List<ItemBacklog> consultarPorProjeto(@PathVariable Integer projetoId) {
    	return new ArrayList<ItemBacklog>(itemBacklogManager.consultarPorProjeto(projetoId));
    }

	/* getters and setters */
	public IItemBacklogManager getItemBacklogManager() {
		return itemBacklogManager;
	}

	public void setItemBacklogManager(IItemBacklogManager itemBacklogManager) {
		this.itemBacklogManager = itemBacklogManager;
	}
	
}
