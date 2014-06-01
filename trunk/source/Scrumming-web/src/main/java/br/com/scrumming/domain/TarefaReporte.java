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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TarefaReporte")
public class TarefaReporte extends ObjetoPersistente<Integer> {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "PK_tarefaReporte")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
	
	@ManyToOne
    @JoinColumn(name = "FK_tarefa", referencedColumnName = "PK_tarefa")
	@JsonBackReference
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
	
    @Column(name = "data_reporte")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataReporte;
	
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

	public Date getDataReporte() {
		return dataReporte;
	}

	public void setDataReporte(Date dataReporte) {
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
		TarefaReporte other = (TarefaReporte) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	
	
}
