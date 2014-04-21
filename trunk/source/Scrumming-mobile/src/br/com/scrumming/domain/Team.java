package br.com.scrumming.domain;


import java.io.Serializable;

import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;




public class Team implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    
    private Projeto projeto;
    
    
    private Empresa empresa;

    
    private Usuario usuario;
    
    
    private PerfilUsuarioEnum perfilUsuario;
    
    
    private boolean isAtivo;

    /**
     * Getters e and setters
     */
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

	public PerfilUsuarioEnum getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuarioEnum perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}

}