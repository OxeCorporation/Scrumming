package br.com.scrumming.domain;

import java.io.Serializable;

public class TeamChave implements Serializable {

	/**
     * Serial Version
     */
	private static final long serialVersionUID = 1L;

	public TeamChave(){}
	
	public TeamChave(Projeto projeto, Usuario usuario, Empresa empresa) {
		this.projeto = projeto;
		this.empresa = empresa;
		this.usuario = usuario;
	}
	
    private Projeto projeto;
    private Empresa empresa;
    private Usuario usuario;
    
    /*getters and setters*/

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}