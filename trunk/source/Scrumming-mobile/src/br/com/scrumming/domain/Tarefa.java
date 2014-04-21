package br.com.scrumming.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import br.com.scrumming.domain.enuns.SituacaoTarefaEnum;
import br.com.scrumming.infra.JodaDateTimeJsonDeserializer;
import br.com.scrumming.infra.JodaDateTimeJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Tarefa implements Serializable {

	/**
     * Serial Version
     */
	private static final long serialVersionUID = 1L;
	
    private Integer codigo;
	private ItemBacklog itemBacklog;
	private Usuario usuario;
    private String nome;
    private String descricao;
    private SituacaoTarefaEnum situacao;
    private Integer tempoEstimado;
	@JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonDeserialize(using = JodaDateTimeJsonDeserializer.class)
	private DateTime dataAtribuicao;
    private String situacaoDescricao;
	private String backgroundColor;
	private boolean estahConcluida;
	
	/*getters and setters*/
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	//@JsonBackReference
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
}