package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class UsuarioEmpresaClientService extends AbstractClientService {

	public List<Empresa> consultarEmpresasPorUsuario(Integer usuarioID) {
		Empresa[] empresas = getRestTemplate()
				.getForObject(
						getURIService(ConstantesService.UsuarioEmpresa.URL_CONSULTAR_EMPRESAS_POR_USUARIO),
						Empresa[].class, usuarioID);
		return Arrays.asList(empresas);
	}

	public List<Usuario> consultarUsuariosPorEmpresa(Integer empresaID) {
		Usuario[] usuarios = getRestTemplate()
				.getForObject(
						getURIService(ConstantesService.UsuarioEmpresa.URL_CONSULTAR_USUARIOS_POR_EMPRESA),
						Usuario[].class, empresaID);
		return Arrays.asList(usuarios);
	}
	
	public List<Usuario> consultarUsuarioAtivosDoProjeto(Integer projetoID, Integer empresaID){
		return Arrays.asList(getRestTemplate().getForObject(getURIService(
				ConstantesService.UsuarioEmpresa.URL_CONSULTAR_USUARIOS_ATIVOS_DO_PROJETO), 
				Usuario[].class, projetoID, empresaID));
	}
}
