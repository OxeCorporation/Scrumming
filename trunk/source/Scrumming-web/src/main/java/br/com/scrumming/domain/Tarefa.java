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
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

@Entity
@Table(name = "Tarefa")
public class Tarefa extends ObjetoPersistente<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "PK_tarefa")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
	
	@ManyToOne
    @JoinColumn(name = "FK_itemBacklog", referencedColumnName = "PK_backlog")
	private ItemBacklog itemBacklog;
	
	@ManyToOne
    @JoinColumn(name = "FK_usuario", referencedColumnName = "PK_usuario")
	private Usuario usuario;
	
	@Column(name = "descricao", columnDefinition = "varchar(500)")
    @NotBlank
    private String descricao;
	
	@Column(name = "situacao_tarefa", columnDefinition = "Integer", length = 11)
	@NotNull
    private Integer situacaoTarefa;
	
	@Column(name = "tempo_estimado", columnDefinition = "Integer", length = 11)
	@NotNull
    private Integer tempoEstimado;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_Atribuicao")
    private DateTime dataAtribuicao;

	
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

	public ItemBacklog getItemBacklog() {
		return itemBacklog;
	}


	public void setItemBacklog(ItemBacklog itemBacklog) {
		this.itemBacklog = itemBacklog;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Integer getSituacaoTarefa() {
		return situacaoTarefa;
	}


	public void setSituacaoTarefa(Integer situacaoTarefa) {
		this.situacaoTarefa = situacaoTarefa;
	}


	public Integer getTempoEstimado() {
		return tempoEstimado;
	}


	public void setTempoEstimado(Integer tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}


	public DateTime getDataAtribuicao() {
		return dataAtribuicao;
	}


	public void setDataAtribuicao(DateTime dataAtribuicao) {
		this.dataAtribuicao = dataAtribuicao;
	}
	
	
}
