package br.com.scrumming.web.infra.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component("ExceptionResolver")
public class ExceptionResolver extends SimpleMappingExceptionResolver {
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(ex);
		response.addHeader("error", ex.getMessage());
		return modelAndView;
	}
}