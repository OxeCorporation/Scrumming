package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class TeamClientService extends AbstractClientService {

	public List<Usuario> consultarUsuarioPorProjeto(Integer projetoID){
		return Arrays.asList(getRestTemplate().getForObject(getURIService(ConstantesService.Team.URL_CONSULTAR_USUARIO_PROJETO), Usuario[].class, projetoID));
	}
	
	public List<Projeto> consultarProjetoPorUsuarioDaEmpresa(Integer usuarioID, Integer empresaID){
		
		return Arrays.asList(getRestTemplate().getForObject(getURIService(
											ConstantesService.Team.CONSULTAR_PROJETO_POR_USUARIO_DA_EMPRESA), 
											Projeto[].class, usuarioID, empresaID));
	}
	
	public Team consultarTimeProjeto(Team time) {
		
		Integer codigoProjeto,codigoEmpresa,codigoUsuario;
		codigoProjeto = time.getProjeto().getCodigo();
		codigoEmpresa = time.getEmpresa().getCodigo();
		codigoUsuario = time.getUsuario().getCodigo();
		
		return getRestTemplate().getForObject(getURIService(ConstantesService.Team.CONSULTAR_TIME_PROJETO), 
				Team.class, codigoProjeto, codigoEmpresa, codigoUsuario);
	}
}