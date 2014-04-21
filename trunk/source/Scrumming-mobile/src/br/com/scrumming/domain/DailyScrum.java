package br.com.scrumming.domain;

import java.util.Date;

import org.joda.time.DateTime;

import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class DailyScrum {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private Sprint sprint;
	private String local;
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataHora;
	
	private Integer duracao;
	
	
	private String dataFormatada;
	
	
	private String horaFormatada;
	
	
	private Date dataHoraCalendar;
	
	
	private boolean uniqueDaily;
	
	private boolean editableDaily;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public DateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(DateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public String getDataFormatada() {
		try {
			dataFormatada =  getDataHora().toString("dd/MM/yyyy");
		} catch (NullPointerException e) {			
		}
		return dataFormatada;
	}

	public String getHoraFormatada() {
		try {
			horaFormatada =  getDataHora().toString("HH:mm");
		} catch (NullPointerException e) {			
		}
		return horaFormatada;
	}

	public Date getDataHoraCalendar() {
		return dataHoraCalendar;
	}

	public void setDataHoraCalendar(Date dataHoraCalendar) {
		this.dataHoraCalendar = dataHoraCalendar;
	}

	public boolean isUniqueDaily() {
		return uniqueDaily;
	}

	public void setUniqueDaily(boolean uniqueDaily) {
		this.uniqueDaily = uniqueDaily;
	}

	public boolean isEditableDaily() {
		return editableDaily;
	}

	public void setEditableDaily(boolean editableDaily) {
		this.editableDaily = editableDaily;
	}
}