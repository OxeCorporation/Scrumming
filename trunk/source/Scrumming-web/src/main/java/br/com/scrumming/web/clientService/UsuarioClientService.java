package br.com.scrumming.web.clientService;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Usuario;

public class UsuarioClientService {

    public Usuario obterUsuario(String login, String senha) {

        RestTemplate template = new RestTemplate();

        String url = "http://localhost:8080/Scrumming/service/usuario/login/{login}/{senha}";

        ResponseEntity<Usuario> usuario =
                template.postForEntity(url, HttpEntity.EMPTY, Usuario.class, login, senha);

        return usuario.getBody();
    }
    
    public String salvarUsuario(Usuario usuario){

    	RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        
        String uri = "http://localhost:8080/Scrumming/service/usuario/usu";
        
        return rt.postForObject(uri, usuario, String.class);
    }
}