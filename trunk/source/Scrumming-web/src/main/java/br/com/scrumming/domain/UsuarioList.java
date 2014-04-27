package br.com.scrumming.domain;

import java.io.Serializable;

import br.com.scrumming.domain.enuns.SituacaoUsuarioList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioList implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private SituacaoUsuarioList situacaoUsuarioList;
	public UsuarioList(){
		usuario = new Usuario();
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public SituacaoUsuarioList getSituacaoUsuarioList() {
		return situacaoUsuarioList;
	}
	public void setSituacaoUsuarioList(SituacaoUsuarioList situacaoUsuarioList) {
		this.situacaoUsuarioList = situacaoUsuarioList;
	}

}
