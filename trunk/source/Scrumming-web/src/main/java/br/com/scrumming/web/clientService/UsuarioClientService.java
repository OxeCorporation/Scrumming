package br.com.scrumming.web.clientService;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Usuario;

public class UsuarioClientService {

    public Usuario obterUsuario(String login, String senha) {

        RestTemplate template = new RestTemplate();

        String url = "http://localhost:8080/Scrumming/service/usuario/login";

        ResponseEntity<Usuario> usuario =
                template.postForEntity(url + "?login={login}&senha={senha}", HttpEntity.EMPTY,
                        Usuario.class, login, senha);
        return usuario.getBody();
    }
}
