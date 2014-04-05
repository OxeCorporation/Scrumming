package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UsuarioEmpresa")
@IdClass(UsuarioEmpresaChave.class)
public class UsuarioEmpresa extends ObjetoPersistente<UsuarioEmpresaChave> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    private Empresa empresa;

	@Id
    private Usuario usuario;

    @Column(name = "is_usuarioEmpresa", columnDefinition = "bit", length = 1)
    private boolean ativo;

    /*Getters e and setters*/
    @Override
    @JsonIgnore
    public UsuarioEmpresaChave getChave() {
        return new UsuarioEmpresaChave(getEmpresa(),getUsuario());
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}