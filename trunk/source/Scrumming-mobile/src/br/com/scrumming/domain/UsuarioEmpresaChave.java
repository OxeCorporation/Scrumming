package br.com.scrumming.domain;

import java.io.Serializable;

public class UsuarioEmpresaChave implements Serializable {

    /**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioEmpresaChave(){}
	
	public UsuarioEmpresaChave(Empresa empresa, Usuario usuario) {
		this.empresa = empresa;
		this.usuario = usuario;
	}
	
    private Empresa empresa;
    private Usuario usuario;

    /* getters and setters */
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