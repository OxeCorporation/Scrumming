package br.com.scrumming.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

@Entity
@Table(name = "Projeto")
public class Projeto extends ObjetoPersistente<Integer> {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getChave() {
		// TODO Auto-generated method stub
		return null;
	}

}
