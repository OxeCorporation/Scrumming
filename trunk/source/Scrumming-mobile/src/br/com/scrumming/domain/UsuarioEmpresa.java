package br.com.scrumming.domain;

import java.io.Serializable;





public class UsuarioEmpresa implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    
    private Empresa empresa;

	
    private Usuario usuario;

    
    private boolean ativo;
    
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}