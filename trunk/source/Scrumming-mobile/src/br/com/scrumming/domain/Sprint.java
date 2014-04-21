package br.com.scrumming.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

import br.com.scrumming.domain.enuns.SituacaoSprintEnum;
import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Sprint implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
   
    private Integer codigo;
    private Projeto projeto;
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
    private DateTime dataRevisao;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataCadastro;
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
    private DateTime dataFechamento;
    private SituacaoSprintEnum situacaoSprint;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private String statusSprint;
    private boolean editable;

    /* getters and setters */
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