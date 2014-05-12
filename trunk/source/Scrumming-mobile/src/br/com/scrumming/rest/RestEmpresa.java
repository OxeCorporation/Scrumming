package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.infra.RestFactory;

public class RestEmpresa {

	/**
	 * Para testes localmente utilizar o ip da maquina local, veja no ipconfing (no windows), ou ifconfig (no linux)
	 * 
	 * Caso contrário utilizar o domineo da aplicacao em produção
	 */
	
	public static List<UsuarioEmpresa> retorneEmpresas(Usuario usuario){
		
		//String domain = "scrumming-agilscrum.rhcloud.com";
		String domain = "192.168.1.2:8080";
		final String url = "http://"+domain+"/Scrumming/service/usuario_empresa/empresadousuario/{usuarioID}";
		UsuarioEmpresa[] usuariosEmpresa = RestFactory.getRestTemplate().getForObject(url, UsuarioEmpresa[].class, usuario.getCodigo());
		return Arrays.asList(usuariosEmpresa);
	}
}