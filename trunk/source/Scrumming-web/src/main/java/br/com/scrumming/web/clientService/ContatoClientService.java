package br.com.scrumming.web.clientService;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.scrumming.domain.Contato;

public class ContatoClientService {

    public List<Contato> findAll() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Contato[]> contatos =
                restTemplate.getForEntity("http://localhost:8080/Scrumming/service/contato",
                        Contato[].class);
        return Arrays.asList(contatos.getBody());
    }

    public List<Contato> consultarContato(String nome) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/Scrumming/service/contato/{nome}";

        ResponseEntity<Contato[]> contatos = restTemplate.getForEntity(url, Contato[].class, nome);

        return Arrays.asList(contatos.getBody());
    }
}
