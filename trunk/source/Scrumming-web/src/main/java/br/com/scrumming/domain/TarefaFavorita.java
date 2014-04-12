package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TarefaFavorita")
@IdClass(TarefaFavoritaChave.class)
public class TarefaFavorita extends ObjetoPersistente<TarefaFavoritaChave> {
	
	/**
     * Serial Version
     */
	private static final long serialVersionUID = 1L;

	@Id
	private Tarefa tarefa;
	@Id
	private Usuario usuario;	
	
	@Column(name = "favorita", columnDefinition = "bit")
    private boolean favorita;
	
		
	@Override
	@JsonIgnore
	public TarefaFavoritaChave getChave() {
		TarefaFavoritaChave chave = new TarefaFavoritaChave();
    	chave.setTarefa(tarefa);
    	chave.setUsuario(usuario);
        return chave;
	}

	
	/**
     * Getters e and setters
     */	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public boolean isFavorita() {
		return favorita;
	}

	public void setFavorita(boolean favorita) {
		this.favorita = favorita;
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
		TarefaFavorita other = (TarefaFavorita) obj;
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
