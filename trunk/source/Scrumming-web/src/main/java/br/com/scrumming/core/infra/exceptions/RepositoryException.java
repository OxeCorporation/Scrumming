package br.com.scrumming.core.infra.exceptions;

public class RepositoryException extends Exception {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	public RepositoryException(Exception exception, String mensagem) {
		super(mensagem, exception);
	}

	public RepositoryException(String mensagem) {
		super(mensagem);
	}

	public RepositoryException(Exception exception) {
		super(exception);
	}
}
