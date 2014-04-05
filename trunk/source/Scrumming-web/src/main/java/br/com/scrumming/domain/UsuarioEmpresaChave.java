package br.com.scrumming.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
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

	@ManyToOne
    @JoinColumn(name = "FK_empresa", referencedColumnName = "PK_empresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
    private Usuario usuario;

	public Empresa getEmpresa() {
		/*getters and setters*/
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