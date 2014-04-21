package br.com.scrumming.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ReporteTarefa implements Serializable {
	
	private static final long serialVersionUID = 1L;
   
    private Integer codigo;
	private Tarefa tarefa;
	private Usuario usuario;
    private Integer tempoReportado;
    private Integer tempoRestante;
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataReporte;

	/*getters and setters*/
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