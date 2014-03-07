package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Contato;
import br.com.scrumming.web.clientService.ContatoClientService;

@ManagedBean
@ViewScoped
public class ExemploMB {

    private List<Contato> contatos;

    private ContatoClientService clientService;
    private String nomeCotato;

    @PostConstruct
    public void construir() {
        clientService = new ContatoClientService();
    }

    public String construirContatos() {
        contatos = clientService.findAll();
        return "";
    }

    public String consultarContato() {
        return null;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public String getNomeCotato() {
        return nomeCotato;
    }

    public void setNomeCotato(String nomeCotato) {
        this.nomeCotato = nomeCotato;
    }
}