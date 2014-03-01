package br.com.scrumming.core.infra.repositorio;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ObjetoPersistente<Chave extends Serializable> implements
		Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Versão do objeto, utilizado para controle de concorrencia
	 */
	public abstract Chave getChave();
}
