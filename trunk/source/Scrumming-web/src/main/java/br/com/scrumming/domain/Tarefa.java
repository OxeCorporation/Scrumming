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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonDeserializer;
import br.com.scrumming.core.infra.util.JodaDateTimeJsonSerializer;
import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Tarefa")
public class Tarefa extends ObjetoPersistente<Integer> {
	
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
	
	@Column(name = "nome", columnDefinition = "varchar(50)")
    @NotBlank
    private String nome;
	
	@Column(name = "descricao", columnDefinition = "varchar(500)")
    @NotBlank
    private String descricao;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao_tarefa", columnDefinition = "Integer", length = 1)
	@NotNull
    private SituacaoTarefaEnum situacao;
	
	@Column(name = "tempo_estimado", columnDefinition = "Integer", length = 11)
	@NotNull
    private Integer tempoEstimado;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
    @Column(name = "data_Atribuicao")
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataAtribuicao;
	
	@Transient
    private String situacaoDescricao;
	
	@Transient
	private String backgroundColor;
	
	@Transient
	private boolean estahConcluida;
	
	@Transient
	private boolean foiFavoritada;
	
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

	@JsonBackReference
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

	public SituacaoTarefaEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoTarefaEnum situacao) {
		this.situacao = situacao;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSituacaoDescricao() {
		return situacaoDescricao;
	}

	public void setSituacaoDescricao(String situacaoDescricao) {
		this.situacaoDescricao = situacaoDescricao;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.codigo).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;

        if (obj == this) {
            isEquals = true;
        } else if (obj instanceof Usuario) {
            Tarefa tarefa = (Tarefa) obj;

            isEquals = new EqualsBuilder().append(this.codigo, tarefa.getCodigo()).isEquals();
        }
        return isEquals;
    }

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isEstahConcluida() {
		estahConcluida = situacao == SituacaoTarefaEnum.FEITO;		
		return estahConcluida;
	}

	public boolean isFoiFavoritada() {
		return foiFavoritada;
	}

	public void setFoiFavoritada(boolean foiFavoritada) {
		this.foiFavoritada = foiFavoritada;
	}
}
