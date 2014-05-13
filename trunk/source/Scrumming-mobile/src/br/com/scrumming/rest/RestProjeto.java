package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.infra.RestFactory;

public class RestProjeto {
	
public static List<Projeto> retorneProjetosPorUsuario(UsuarioEmpresa usuarioEmpresa){
		
//		String domain = "scrumming-agilscrum.rhcloud.com";
		String domain = "192.168.0.101:8080";
		Integer usuarioID = usuarioEmpresa.getUsuario().getCodigo();
		Integer empresaID = usuarioEmpresa.getEmpresa().getCodigo();
		
		final String url = "http://"+domain+"/Scrumming/service/team/listProjetos/{usuarioID}/{empresaID}";
		Projeto[] projetos = RestFactory.getRestTemplate().getForObject(url, Projeto[].class, usuarioID, empresaID);
		return Arrays.asList(projetos);
	}

}
