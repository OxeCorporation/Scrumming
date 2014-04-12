package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintBacklogManager;
import br.com.scrumming.core.manager.interfaces.ISprintManager;
import br.com.scrumming.core.repositorio.ItemBacklogRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.SprintBacklog;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;

@Service
public class ItemBacklogManager extends AbstractManager<ItemBacklog, Integer> implements IItemBacklogManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ItemBacklogRepositorio itemRepositorio;
    
    @Autowired
    private ISprintBacklogManager sprintBacklogManager;
    
    private double roi;
    
    @Autowired
    private ISprintManager sprintManager;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
	public void salvarItemBacklog(ItemBacklog itemBacklog) {
    	
    	if (itemBacklog.getCodigo() != null) {
			insertOrUpdate(itemBacklog);
		}else{
    		roi =  itemBacklog.getValorNegocio() / itemBacklog.getStoryPoints();
    		itemBacklog.setRoi(roi);    		
    		itemBacklog.setAtivo(true);
    		itemBacklog.setSituacaoBacklog(SituacaoItemBacklogEnum.FAZER);
    		insertOrUpdate(itemBacklog);
		}
	}
    
    @Override
    @Transactional(rollbackFor = Exception.class)
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
    	List<ItemBacklog> listaDeItens= itemRepositorio.consultarPorProjeto(projetoID);
    	List<ItemBacklog> item= new ArrayList<>();
    	for (ItemBacklog itembacklog : listaDeItens){
    		if (itembacklog.getSituacaoBacklog() == SituacaoItemBacklogEnum.FAZER) {
				itembacklog.setStatusItembacklog("Fazer");
			}else if (itembacklog.getSituacaoBacklog() == SituacaoItemBacklogEnum.FAZENDO) {
				itembacklog.setStatusItembacklog("Fazendo");
			}else if (itembacklog.getSituacaoBacklog() == SituacaoItemBacklogEnum.FEITO) {
				itembacklog.setStatusItembacklog("Feito");
			}
    		item.add(itembacklog);
    	}
    	return item;
	}
        
    
    /**
     * Consulta os ítens disponiveis do projeto.
     */
    public List<ItemBacklog> consultarItensDisponiveisPorProjeto(Integer projetoID) {
    	
    	List<ItemBacklog> productBacklog = consultarPorProjeto(projetoID);
    	List<ItemBacklog> itensDisponiveis = new ArrayList<>();
    	
    	// Percorre todos os itens do backlog para verificar os que não foram
		// atribuidos às Sprints
		for (ItemBacklog item : productBacklog) {
			List<SprintBacklog> spBacklog = sprintBacklogManager.consultarAtivoPorItem(item);
			if (spBacklog == null || spBacklog.size() <= 0) {
				itensDisponiveis.add(item);
			}
		}
    	return itensDisponiveis;
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