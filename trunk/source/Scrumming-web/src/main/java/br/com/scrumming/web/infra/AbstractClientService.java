package br.com.scrumming.web.infra;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.core.infra.util.ApplicationPropertiesUtil;
import br.com.scrumming.core.infra.util.ConstantesApplication;

public abstract class AbstractClientService {

	private static final String APPLICATION_DOMAIN = ApplicationPropertiesUtil
			.get(ConstantesApplication.APLICATION_DOMAIN);
	private static final String APPLICATION_SERVICE_PATH = ApplicationPropertiesUtil.get(ConstantesApplication.APLICATION_PATH_SERVICE);
	protected String getURIService(String service) {
		return APPLICATION_DOMAIN + APPLICATION_SERVICE_PATH + service;
	}

	protected RestTemplate getRestTemplate() {
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters()
				.add(new MappingJackson2HttpMessageConverter());
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		return rt;
	}
}