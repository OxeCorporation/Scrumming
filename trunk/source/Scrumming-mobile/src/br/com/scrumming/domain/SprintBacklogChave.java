package br.com.scrumming.domain;

import java.io.Serializable;


public class SprintBacklogChave implements Serializable {

	private static final long serialVersionUID = 1L;


    private ItemBacklog itemBacklog;
    
    
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
