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
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "ReporteTarefa")
public class ReporteTarefa extends ObjetoPersistente<Integer> {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "PK_reporteTarefa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
	
	@ManyToOne
    @JoinColumn(name = "FK_tarefa", referencedColumnName = "PK_tarefa")
	private Tarefa tarefa;
	
	@ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
	private Usuario usuario;
	
	@Column(name = "tempo_reportado", columnDefinition = "Integer", length = 11)
	@NotNull
    private Integer tempoReportado;
	
	@Column(name = "tempo_restante", columnDefinition = "Integer", length = 11)
	@NotNull
    private Integer tempoRestante;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_reporte")
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataReporte;
	
	@Override
	@JsonIgnore
	public Integer getChave() {
		return this.codigo;
	}

	/**
     * Getters e and setters
     */
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Integer getTempoReportado() {
		return tempoReportado;
	}

	public void setTempoReportado(Integer tempoReportado) {
		this.tempoReportado = tempoReportado;
	}

	public Integer getTempoRestante() {
		return tempoRestante;
	}

	public void setTempoRestante(Integer tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	public DateTime getDataReporte() {
		return dataReporte;
	}

	public void setDataReporte(DateTime dataReporte) {
		this.dataReporte = dataReporte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReporteTarefa other = (ReporteTarefa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	
	
}
