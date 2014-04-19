package br.com.scrumming.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TeamChave implements Serializable {

	private static final long serialVersionUID = 1L;

	public TeamChave(){}
	
	public TeamChave(Projeto projeto, Usuario usuario, Empresa empresa) {
		this.projeto = projeto;
		this.empresa = empresa;
		this.usuario = usuario;
	}
	
	@ManyToOne
    @JoinColumn(name="FK_projeto", referencedColumnName="PK_projeto")
    private Projeto projeto;
    
    @ManyToOne
    @JoinColumn(name="FK_empresa", referencedColumnName="PK_empresa")
    private Empresa empresa;
	
    @ManyToOne
    @JoinColumn(name="FK_usuario", referencedColumnName="PK_usuario")
    private Usuario usuario;
    

    /*Getters & Setters*/

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
