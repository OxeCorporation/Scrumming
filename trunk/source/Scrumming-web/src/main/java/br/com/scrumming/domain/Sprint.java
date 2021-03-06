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
import br.com.scrumming.domain.enuns.SituacaoSprintEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Sprint")
public class Sprint extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_sprint")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
    
    @ManyToOne
    @JoinColumn(name="FK_projeto", referencedColumnName="PK_projeto")
    private Projeto projeto;

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
    @Column(name = "data_revisao")
    @NotNull
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataRevisao;
    
    @Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_cadastro")
    @NotNull
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataCadastro;
    
    @Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_fechamento")
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataFechamento;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "situacao_sprint", columnDefinition = "Integer", length = 1)
    private SituacaoSprintEnum situacaoSprint;
    
    @Transient
    private String dataInicioFormatada;

    @Transient
    private String dataFimFormatada;
    
    @Transient
    private String statusSprint;
    
    @Transient
    private boolean editable;

    /* getters and setters */
    @Override
    @JsonIgnore
    public Integer getChave() {
        return this.codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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
    	/*int year, month, date, hourOfDay, minute, second;
		Calendar cal = new GregorianCalendar();
		year = dataInicio.getYear();
		month = dataInicio.getMonthOfYear() - 1;
		date = dataInicio.getDayOfMonth();
		hourOfDay = 0;
		minute = 0;
		second = 0;
		cal.set(year, month, date, hourOfDay, minute, second);
		cal.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
        this.dataInicio = new DateTime(cal);*/
    	this.dataInicio = dataInicio;
    }

    public DateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(DateTime dataFim) {
    	/*int year, month, date, hourOfDay, minute, second;
		Calendar cal = new GregorianCalendar();
		year = dataFim.getYear();
		month = dataFim.getMonthOfYear() - 1;
		date = dataFim.getDayOfMonth();
		hourOfDay = 23;
		minute = 59;
		second = 59;
		cal.set(year, month, date, hourOfDay, minute, second);
		cal.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
    	this.dataFim = new DateTime(cal);*/
    	this.dataFim = dataFim;
    }

    public DateTime getDataRevisao() {
        return dataRevisao;
    }

    public void setDataRevisao(DateTime dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public DateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(DateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
    
    public DateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(DateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public SituacaoSprintEnum getSituacaoSprint() {
		return situacaoSprint;
	}

	public void setSituacaoSprint(SituacaoSprintEnum situacaoSprint) {
		this.situacaoSprint = situacaoSprint;
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

	public String getStatusSprint() {
		return statusSprint;
	}

	public void setStatusSprint(String statusSprint) {
		this.statusSprint = statusSprint;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}