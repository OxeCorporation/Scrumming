package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.repositorio.ItemBacklogRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;

@Service
public class ItemBacklogManager extends AbstractManager<ItemBacklog, Integer> implements IItemBacklogManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ItemBacklogRepositorio itemRepositorio;
    
    @Override
	public void salvarItemBacklog(ItemBacklog itemBacklog) {
    	
    	
    	
    		/**roi = itemBacklog.getValorNegocio() / itemBacklog.getStoryPoints();
    		itemBacklog.setRoi(roi);
    		*/
    		
    		itemBacklog.setSituacaoBacklog(SituacaoItemBacklogEnum.FAZER);
    		insertOrUpdate(itemBacklog);
	}
    
    @Override
	public void cancelarItem(ItemBacklog item) {
    	if (item.getCodigo() != null){
			if (item.isAtivo() == true) {
				item.setAtivo(false);
			}else{
				item.setAtivo(true);
			}
		insertOrUpdate(item);
	    }
	}
    
    @Override
	public List<ItemBacklog> consultarPorProjeto(Integer projetoID) {
    	return itemRepositorio.consultarPorProjeto(projetoID);
	}
    
    public ItemBacklog consultarItemPorID(Integer itemID) {
    	return itemRepositorio.consultarItemPorID(itemID);
    }
    
    @Override
    public AbstractRepositorio<ItemBacklog, Integer> getRepositorio() {
        return this.itemRepositorio;
    }
    
	/*Getters and Setters*/
	public ItemBacklogRepositorio getItemRepositorio() {
		return itemRepositorio;
	}

	public void setItemRepositorio(ItemBacklogRepositorio itemRepositorio) {
		this.itemRepositorio = itemRepositorio;
	}

}