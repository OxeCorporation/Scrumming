package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SprintBacklog")
@IdClass(SprintBacklogChave.class)
public class SprintBacklog extends ObjetoPersistente<SprintBacklogChave> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    private ItemBacklog itemBacklog;
    
    @Id
    private Sprint sprint;

    @Column(name = "is_ativo", columnDefinition = "bit")
    private boolean isAtivo;

    /* getters and setters */
    @Override
    @JsonIgnore
    public SprintBacklogChave getChave() {
    	SprintBacklogChave chave = new SprintBacklogChave();
    	chave.setItemBacklog(itemBacklog);
    	chave.setSprint(sprint);
        return chave;
    }

	public ItemBacklog getItemBacklog() {
		return itemBacklog;
	}

	public void setItemBacklog(ItemBacklog itemBacklog) {
		this.itemBacklog = itemBacklog;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}  
}