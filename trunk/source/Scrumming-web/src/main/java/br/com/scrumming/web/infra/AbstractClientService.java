package br.com.scrumming.web.infra;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
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
		MimeType type = MimeTypeUtils.parseMimeType("text/plain;charset=UTF-8");
		List<MediaType> mediaTypes = Arrays.asList(new MediaType(type.getType(), type.getSubtype(),
				type.getParameters()));
		
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json",  Charset.forName("UTF-8"))));
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
		messageConverters.add(mappingJackson2HttpMessageConverter);
		messageConverters.add(stringHttpMessageConverter);
		return messageConverters;
	}
	
	protected RestTemplate getRestTemplate() {
		return this;
	}
}