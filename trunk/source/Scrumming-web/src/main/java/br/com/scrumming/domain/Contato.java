package br.com.scrumming.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistenteVersionado;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CONTATO")
@AttributeOverride(name = "ultimaAtualizacao", column = @Column(name = "CL_ULTIMA_ATUALIZACAO"))
public class Contato extends ObjetoPersistenteVersionado<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    private static final String COLUNA_ID = "NUMERO";

    @Id
    @GeneratedValue
    @Column(name = COLUNA_ID)
    private Integer numero;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SOBRENOME")
    private String sobreNome;

    @Override
    @JsonIgnore
    public Integer getChave() {
        return getNumero();
    }

    /* getters and setters */
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.numero).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;

        if (obj == this) {
            isEquals = true;
        } else if (obj instanceof Contato) {
            Contato con = (Contato) obj;
            isEquals = new EqualsBuilder().append(numero, con.getNumero()).isEquals();
        }
        return isEquals;
    }
}