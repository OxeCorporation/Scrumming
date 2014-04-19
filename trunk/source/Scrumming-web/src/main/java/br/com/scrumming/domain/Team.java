package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.domain.enuns.PerfilUsuarioEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TimeProjeto")
@IdClass(TeamChave.class)
public class Team extends ObjetoPersistente<TeamChave> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Projeto projeto;
    
    @Id
    private Empresa empresa;

    @Id
    private Usuario usuario;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "perfil_usuario", columnDefinition = "Integer", length = 1)
    private PerfilUsuarioEnum perfilUsuario;
    
    @Column(name = "is_ativo", columnDefinition = "bit")
    private boolean isAtivo;
    
    @Override
    @JsonIgnore
    public TeamChave getChave() {
        return new TeamChave(getProjeto(),getUsuario(),getEmpresa());
    }

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