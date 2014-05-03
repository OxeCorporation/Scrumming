package br.com.scrumming.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TarefaFavoritaChave implements Serializable {

	/**
     * Serial Version
     */
	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name = "FK_tarefa", referencedColumnName = "PK_tarefa")
	private Tarefa tarefa;
	
	@ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
	private Usuario usuario;
	
	public TarefaFavoritaChave(){}
	
	public TarefaFavoritaChave(Tarefa tarefa, Usuario usuario){
		this.tarefa = tarefa;
		this.usuario = usuario;
	}

	
	/*Getters & Setters*/
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tarefa == null) ? 0 : tarefa.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarefaFavoritaChave other = (TarefaFavoritaChave) obj;
		if (tarefa == null) {
			if (other.tarefa != null)
				return false;
		} else if (!tarefa.equals(other.tarefa))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}   
	
}
