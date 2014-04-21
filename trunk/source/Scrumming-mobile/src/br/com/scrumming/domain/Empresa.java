package br.com.scrumming.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Empresa implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
    private Integer codigo;
    private String nome;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataCadastro;
    private boolean isAtivo;

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
        
    public DateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(DateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
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
        } else if (obj instanceof Empresa) {
        	Empresa empresa = (Empresa) obj;

            isEquals = new EqualsBuilder().append(this.codigo, empresa.getCodigo()).isEquals();
        }
        return isEquals;
    }
}