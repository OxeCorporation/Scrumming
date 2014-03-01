package br.com.scrumming.core.infra.exceptions;

public class NegocioException extends RuntimeException {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    public NegocioException(Exception exception, String mensagem) {
        super(mensagem, exception);
    }

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(Exception exception) {
        super(exception);
    }
}
