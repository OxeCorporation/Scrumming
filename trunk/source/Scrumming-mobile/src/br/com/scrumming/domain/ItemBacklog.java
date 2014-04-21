package br.com.scrumming.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;

public class ItemBacklog {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

   
    private Integer codigo;

   
    private Projeto projeto;

   
    private String nome;

   
    private String descricao;

   
    private String criterioAceitacao;

   
    private Double valorNegocio;

  
    private Integer storyPoints;

  
    private Double roi;

   
    private SituacaoItemBacklogEnum situacaoBacklog;
    
  
    private boolean isAtivo;
    
   
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();
    
  
    private String statusItembacklog;
    
   
    private boolean deliverable;
    
   
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

    public String getCriterioAceitacao() {
        return criterioAceitacao;
    }

    public void setCriterioAceitacao(String criterioAceitacao) {
        this.criterioAceitacao = criterioAceitacao;
    }

    public Double getValorNegocio() {
        return valorNegocio;
    }

    public void setValorNegocio(Double valorNegocio) {
        this.valorNegocio = valorNegocio;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    
    public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
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
        } else if (obj instanceof ItemBacklog) {
        	ItemBacklog item = (ItemBacklog) obj;

            isEquals = new EqualsBuilder().append(this.codigo, item.getCodigo()).isEquals();
        }
        return isEquals;
    }

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public String getStatusItembacklog() {
		return statusItembacklog;
	}

	public void setStatusItembacklog(String statusItembacklog) {
		this.statusItembacklog = statusItembacklog;
	}

	public boolean isDeliverable() {
		return deliverable;
	}

	public void setDeliverable(boolean deliverable) {
		this.deliverable = deliverable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public SituacaoItemBacklogEnum getSituacaoBacklog() {
		return situacaoBacklog;
	}

	public void setSituacaoBacklog(SituacaoItemBacklogEnum situacaoBacklog) {
		this.situacaoBacklog = situacaoBacklog;
	}
}