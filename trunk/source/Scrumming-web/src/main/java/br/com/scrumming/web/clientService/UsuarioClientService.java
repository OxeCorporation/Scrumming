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

	public String salvarUsuario(Usuario usuario) {
		return getRestTemplate().postForObject(
				getURIService(ConstantesService.Usuario.SALVAR_USUARIO),
				usuario, String.class);
	}
}
