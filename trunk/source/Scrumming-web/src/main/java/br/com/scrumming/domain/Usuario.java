package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Usuario")
public class Usuario extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "foto", columnDefinition = "LONGBLOB")
    private byte[] foto;
    
    @Column(name = "nome", columnDefinition = "varchar(50)")
    @NotBlank
    private String nome;

    @Column(name = "email", columnDefinition = "varchar(50)")
    @Email
    private String email;

    @Column(name = "login", columnDefinition = "varchar(30)")
    private String login;

    @Column(name = "senha", columnDefinition = "varchar(32)")
    private String senha;

    @Column(name = "is_ativo", columnDefinition = "bit", length = 1)
    private boolean ativo;

    @Column(name = "is_empresa", columnDefinition = "bit", length = 1)
    private boolean empresa;

    @Override
    @JsonIgnore
    public Integer getChave() {
        return this.codigo;
    }

    /* getters and setters */
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }
    
    public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.codigo).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;

        if (obj == this) {
            isEquals = true;
        } else if (obj instanceof Usuario) {
            Usuario usuario = (Usuario) obj;

            isEquals = new EqualsBuilder().append(this.codigo, usuario.getCodigo()).isEquals();
        }
        return isEquals;
    }
}