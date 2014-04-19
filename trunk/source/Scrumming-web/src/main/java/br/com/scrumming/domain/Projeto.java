package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;
import br.com.scrumming.domain.enuns.SituacaoProjetoEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Projeto")
public class Projeto extends ObjetoPersistente<Integer> {

	// Teste
    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_projeto")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name="FK_empresa", referencedColumnName="PK_empresa")
    private Empresa empresa;

    @Column(name = "nome", columnDefinition = "varchar(50)")
    @NotBlank
    private String nome;

    @Column(name = "descricao", columnDefinition = "varchar(500)")
    @NotBlank
    private String descricao;

    @Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_inicio")
    @NotNull
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataInicio;

    @Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_fim")
    @NotNull
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataFim;

    @Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_cadastro")
    @NotNull
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataCadastro;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "situacao_projeto", columnDefinition = "Integer", length = 1)
    private SituacaoProjetoEnum situacaoProjeto;

    @Transient
    private String dataInicioFormatada;
    
    @Transient
    private String dataFimFormatada;
    
    @Transient
    private String statusProjeto;

    
    @Transient
    private boolean editable = true;

    
    /**
     * Getters e and setters
     */
    @Override
    @JsonIgnore
    public Integer getChave() {
        return this.codigo;
    }
    
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