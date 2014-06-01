package br.com.scrumming.rest;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;
import br.com.scrumming.infra.RestFactory;
import br.com.scrumming.rest.constantes.ConstantesService;

public class RestProjeto {
	
public static List<Projeto> retorneProjetosPorUsuario(UsuarioEmpresa usuarioEmpresa){
		
		Integer usuarioID = usuarioEmpresa.getUsuario().getCodigo();
		Integer empresaID = usuarioEmpresa.getEmpresa().getCodigo();
		
		final String url = "http://"+ConstantesService.DOMAIN_LOCAL+"/Scrumming/service/team/listProjetos/{usuarioID}/{empresaID}";
		Projeto[] projetos = RestFactory.getRestTemplate().getForObject(url, Projeto[].class, usuarioID, empresaID);
		return Arrays.asList(projetos);
	}

}
