package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IItemBacklogManager;
import br.com.scrumming.core.manager.interfaces.IProjetoManager;
import br.com.scrumming.core.repositorio.ItemBacklogRepositorio;
import br.com.scrumming.core.repositorio.ProjetoRepositorio;
import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Projeto;

@Service
public class ItemBacklogManager extends AbstractManager<ItemBacklog, Integer> implements IItemBacklogManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ItemBacklogRepositorio itemRepositorio;
    

	@Override
    public AbstractRepositorio<ItemBacklog, Integer> getRepositorio() {
        return this.itemRepositorio;
    }

	public ItemBacklogRepositorio getItemRepositorio() {
		return itemRepositorio;
	}

	public void setItemRepositorio(ItemBacklogRepositorio itemRepositorio) {
		this.itemRepositorio = itemRepositorio;
	}

   
}