package br.com.scrumming.web.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.core.infra.util.ApplicationPropertiesUtil;
import br.com.scrumming.core.infra.util.ConstantesApplication;

public abstract class AbstractClientService extends RestTemplate {


	public AbstractClientService() {
		super(getMenssageConverter());
	}
	
	private static final String APPLICATION_DOMAIN = ApplicationPropertiesUtil
			.get(ConstantesApplication.APLICATION_DOMAIN);
	private static final String APPLICATION_SERVICE_PATH = ApplicationPropertiesUtil
			.get(ConstantesApplication.APLICATION_PATH_SERVICE);

	protected String getURIService(String service) {
		return APPLICATION_DOMAIN + APPLICATION_SERVICE_PATH + service;
	}

	private static List<HttpMessageConverter<?>> getMenssageConverter(){
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		return messageConverters;
	}
	
	protected RestTemplate getRestTemplate() {
		return this;
	}
}