package br.com.scrumming.web.clientService;

import org.springframework.http.HttpEntity;

import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.infra.AbstractClientService;
import br.com.scrumming.web.infra.ConstantesService;

public class UsuarioClientService extends AbstractClientService {

	public Usuario obterUsuario(String login, String senha) {

		return getRestTemplate().postForEntity(
				getURIService(ConstantesService.Usuario.OBTER_USUARIO_LOGIN),
				HttpEntity.EMPTY, Usuario.class, login, senha).getBody();
	}
	
	public Usuario obterUsuario(Long idUsuarioSelecionado) {
		
		return getRestTemplate().getForObject(getURIService(ConstantesService.Usuario.URL_CONSULTAR), Usuario.class, idUsuarioSelecionado);
	}

	public String salvarUsuario(Usuario usuario) {
		return getRestTemplate().postForObject(
				getURIService(ConstantesService.Usuario.SALVAR_USUARIO),
				usuario, String.class);
	}

	public void salvarUsuario(Usuario usuario, Integer empresaID) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Usuario.SALVAR_USUARIO_EMP),
				usuario, void.class, empresaID);
	}

	public void desativarUsuario(Integer usuarioID, Integer empresaID) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Usuario.DESATIVAR_USUARIO),
				HttpEntity.EMPTY, void.class, usuarioID, empresaID);
	}

	public void ativarUsuario(Integer usuarioID, Integer empresaID) {
		getRestTemplate().postForObject(
				getURIService(ConstantesService.Usuario.ATIVAR_USUARIO),
				HttpEntity.EMPTY, void.class, usuarioID, empresaID);
	}
}
