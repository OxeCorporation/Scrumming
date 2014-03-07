package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TimeProjeto")
public class Team extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "FK_empresa", referencedColumnName = "PK_empresa")
    private Empresa Empresa;

    @ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
    private Usuario usuario;
    


    /**
     * Getters e and setters
     */
    @Override
    @JsonIgnore
    public Integer getChave() {
        return this.codigo;
    }

    
}