package br.com.scrumming.domain;



public class SprintBacklog {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    
    private ItemBacklog itemBacklog;
    
   
    private Sprint sprint;

    
    private boolean isAtivo;

    /* getters and setters */
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
