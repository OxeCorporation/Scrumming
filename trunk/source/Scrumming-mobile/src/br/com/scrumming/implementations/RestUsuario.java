package br.com.scrumming.implementations;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Usuario;

public class RestUsuario {

	public static Usuario retorneUsuario(String login, String senha){
		
		final String url = "http://localhost:8080/Scrumming/service/usuario/login/{login}/{senha}";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		Map<String, String> vars = Collections.singletonMap(login, senha);
		Usuario usuario = restTemplate.getForObject(url, Usuario.class, vars);
		return usuario;
	}
}