package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SprintBacklog")
public class SprintBacklog extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name="FK_backlog", referencedColumnName="PK_backlog")
    private ItemBacklog itemBacklog;
    
    @Id
    @ManyToOne
    @JoinColumn(name="FK_sprint", referencedColumnName="PK_sprint")
    private Sprint sprint;

    @Column(name = "is_ativo", columnDefinition = "bit")
    private boolean isAtivo;

    /* getters and setters */
    @Override
    @JsonIgnore
    public Integer getChave() {
        return null;
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