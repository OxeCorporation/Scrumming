package br.com.scrumming.core.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.scrumming.core.manager.interfaces.IContatoManager;
import br.com.scrumming.domain.Contato;

@Controller
@RequestMapping("/contato")
public class ContatoService {

	@Autowired
	private IContatoManager contatoManager;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<Contato> findAll() {
		return this.contatoManager.findAll();
	}
		@RequestMapping(method = RequestMethod.GET, value = "/{nome}")
		public @ResponseBody Collection<Contato> consultarContato(@PathVariable String nome){
			if(nome != null && !nome.isEmpty()){
				return contatoManager.consultarPorCampo("nome", nome);
			}else{
				return this.contatoManager.findAll();
			}
		}
	
	/* gettters and setters */
	public IContatoManager getContatoManager() {
		return contatoManager;
	}

	public void setContatoManager(IContatoManager contatoManager) {
		this.contatoManager = contatoManager;
	}
}