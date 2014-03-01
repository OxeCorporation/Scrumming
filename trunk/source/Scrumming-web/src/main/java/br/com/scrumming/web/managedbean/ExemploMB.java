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

	private String mensagem;;

	private ContatoClientService clientService;

	@PostConstruct
	public void construir() {
		clientService = new ContatoClientService();
	}

	public String construirContatos() {
		contatos = clientService.findAll();

		return "";
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}