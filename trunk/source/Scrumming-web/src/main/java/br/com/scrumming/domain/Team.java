package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TimeProjeto")
public class Team extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "FK_projeto", referencedColumnName = "PK_projeto")
    private Empresa projeto;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "FK_empresa", referencedColumnName = "PK_empresa")
    private Empresa empresa;

    @Id
    @ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
    private Usuario usuario;
    
    @Column(name = "perfil_usuario", columnDefinition = "int")
    private Integer perfilUsuario;

    /**
     * Getters e and setters
     */
    @Override
    @JsonIgnore
    public Integer getChave() {
        return null;
    }

	public Empresa getProjeto() {
		return projeto;
	}

	public void setProjeto(Empresa projeto) {
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

	public Integer getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(Integer perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}    
}