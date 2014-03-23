package br.com.scrumming.core.infra.exceptions;

public class ObjectNotFoundException extends Exception {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(Exception exception, String mensagem) {
		super(mensagem, exception);
	}

	public ObjectNotFoundException(String mensagem) {
		super(mensagem);
	}

	public ObjectNotFoundException(Exception exception) {
		super(exception);
	}
}
