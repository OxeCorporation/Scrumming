package br.com.scrumming.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SprintBacklogChave implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name="FK_backlog", referencedColumnName="PK_backlog")
    private ItemBacklog itemBacklog;
    
    @ManyToOne
    @JoinColumn(name="FK_sprint", referencedColumnName="PK_sprint")
    private Sprint sprint;
	
    /*Getters & Setters*/
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
}
