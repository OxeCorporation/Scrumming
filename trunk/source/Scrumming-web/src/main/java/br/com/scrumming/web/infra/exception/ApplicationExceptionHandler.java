package br.com.scrumming.web.infra.exception;

import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;
import org.springframework.web.client.HttpClientErrorException;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.web.infra.FacesMessageUtil;


public class ApplicationExceptionHandler extends ExceptionHandlerWrapper{

	
	private final ExceptionHandler wrappedException;
	
	private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);
	
	public ApplicationExceptionHandler(ExceptionHandler exceptionHandler){
		this.wrappedException = exceptionHandler;
	}
	
	
	@Override
	public ExceptionHandler getWrapped() {
		return wrappedException;
	}

	
	@Override
	public void handle() throws FacesException {
		List<ExceptionQueuedEvent> events = getEvents();
		
		try{
			for (ExceptionQueuedEvent event : events) {
				if(events != null && event.getContext() != null){
					ExceptionQueuedEventContext context = event.getContext();
					Throwable exception = context.getException();
					
					HttpClientErrorException negocioException = getNegocioException(exception);
					if (negocioException == null) {

						String mensagem = exception.getCause() == null ? exception.getMessage() : exception.getCause()
								.getMessage();

						exception.printStackTrace(); 
						LOGGER.error(mensagem);
						FacesMessageUtil.adicionarMensagemErro(ConstantesMensagem.ERRO_INESPERADO);

					} else {
						String chaveErro = negocioException.getResponseHeaders().get("error").get(0);
						LOGGER.error(chaveErro);
						FacesMessageUtil.adicionarMensagemErro(chaveErro);
					}
					
				}
			}
		}catch (Exception e) {
			LOGGER.error(e);
		} finally {
			try {
				events.clear();
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
		try {
			super.handle();
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	private HttpClientErrorException getNegocioException(Throwable exception) {
		if (exception == null) {
			return null;
		}

		if (exception instanceof HttpClientErrorException) {
			return (HttpClientErrorException) exception;
		} else {
			return getNegocioException(exception.getCause());
		}
	}
	
	private List<ExceptionQueuedEvent> getEvents() {
		List<ExceptionQueuedEvent> result;
		Iterable<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents();

		if (events instanceof List) {
			result = (List<ExceptionQueuedEvent>) events;
		} else {
			result = new ArrayList<ExceptionQueuedEvent>();
		}
		return result;
	}
}
