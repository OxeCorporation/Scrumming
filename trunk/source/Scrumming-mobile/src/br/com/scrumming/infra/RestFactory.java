package br.com.scrumming.infra;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
/**
 * Fabrica de instancias do RestTemplate
 * @author esdras
 */
public class RestFactory {
	
	private static List<HttpMessageConverter<?>> getMenssageConverter(){
		Charset charset = Charset.forName("UTF-8");
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json",  charset)));
		
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text","plain",
				charset)));
		
		messageConverters.add(mappingJackson2HttpMessageConverter);
		messageConverters.add(stringHttpMessageConverter);
		return messageConverters;
	}
	
	public static RestTemplate getRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory requesrFactory = (SimpleClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		requesrFactory.setReadTimeout(40 * 1000);
		requesrFactory.setConnectTimeout(40 * 1000);
		restTemplate.setMessageConverters(getMenssageConverter());
		return restTemplate;
	}
}
