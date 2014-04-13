package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("serial")
@Entity
@Table(name = "DailyScrum")
public class DailyScrum extends ObjetoPersistente<Integer> {

	@Id
	@Column(name = "PK_dailyScrum")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;
	
	@ManyToOne
    @JoinColumn(name="FK_sprint", referencedColumnName="PK_sprint")
	private Sprint sprint;
	
	@Column(name = "local_meeting", columnDefinition = "varchar(50)")
	private String local;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "dataHoraMarcada")
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataHora;
	
	@Column(name = "duracao", columnDefinition = "varchar(11)")
	private Integer duracao;
	
	@Override
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
}
