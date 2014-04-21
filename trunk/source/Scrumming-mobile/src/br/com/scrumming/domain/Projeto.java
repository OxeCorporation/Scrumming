package br.com.scrumming.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;
import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Projeto implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
   
    private Integer codigo;
    private Empresa empresa;
    private String nome;
    private String descricao;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataInicio;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataFim;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataCadastro;
    private SituacaoProjetoEnum situacaoProjeto;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private String statusProjeto;
    private boolean editable = true;

    /*getters and setters*/
    public String getStatusProjeto() {
		return statusProjeto;
	}

	public void setStatusProjeto(String statusProjeto) {
		this.statusProjeto = statusProjeto;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(DateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public DateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(DateTime dataFim) {
        this.dataFim = dataFim;
    }

    public DateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(DateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

	public SituacaoProjetoEnum getSituacaoProjeto() {
		return situacaoProjeto;
	}

	public void setSituacaoProjeto(SituacaoProjetoEnum situacaoProjeto) {
		this.situacaoProjeto = situacaoProjeto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDataInicioFormatada() {
		try {
			dataInicioFormatada =  getDataInicio().toString("dd/MM/yyyy");
		} catch (NullPointerException e) {			
		}
		return dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		try {
			dataFimFormatada =  getDataFim().toString("dd/MM/yyyy");
		} catch (NullPointerException e) {			
		}		
		return dataFimFormatada;
	}
}