package br.com.scrumming.rest;

import org.springframework.http.HttpEntity;

import br.com.scrumming.domain.Usuario;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestUsuario {

	/**
	 * Para testes localmente utilizar o ip da maquina local, veja no ipconfing (no windows), ou ifconfig (no linux)
	 * 
	 * Caso contrário utilizar o domineo da aplicacao em produção
	 */
	
	public static Usuario retorneUsuario(String login, String senha){
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/usuario/login/{login}/{senha}";
		Usuario usuario = RestFactory.getRestTemplate().postForObject(url, HttpEntity.EMPTY, Usuario.class,login,senha);
		return usuario;
	}
}