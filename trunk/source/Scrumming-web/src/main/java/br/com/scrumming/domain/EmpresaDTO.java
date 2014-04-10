package br.com.scrumming.domain;

import java.io.Serializable;

public class EmpresaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Empresa empresa;
	
	private Usuario usuario;

	public EmpresaDTO(){
		empresa = new Empresa();
		usuario = new Usuario();
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