package br.com.scrumming.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "DailyScrum")
public class DailyScrum extends ObjetoPersistente<Integer> {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PK_dailyScrum")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;
	
	@ManyToOne
    @JoinColumn(name="FK_sprint", referencedColumnName="PK_sprint")
	@NotNull
	private Sprint sprint;
	
	@Column(name = "descricao", columnDefinition = "varchar(500)")
	private String descricao;
	
	@Column(name = "local_meeting", columnDefinition = "varchar(50)")
	@NotBlank
	private String local;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "dataHoraMarcada")
	@NotNull
	@Future(message="O dia do Daily deve ser posterior à data atual")
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataHora;
	
	@NotNull
	@Column(name = "duracao", columnDefinition = "varchar(11)")
	private Integer duracao;
	
	@Transient
	private String dataFormatada;
	
	@Transient
	private String horaFormatada;
	
	@Transient
	@NotNull
	private Date dataHoraCalendar;
	
	@Transient
	private boolean uniqueDaily;
	
	@Transient
	private boolean editableDaily;
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		if (dataHoraCalendar != null) {
			return dataHoraCalendar;
		} else {
			if (dataHora != null) {
				return dataHora.toDate();
			} else {
				return null;
			}
		}
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
