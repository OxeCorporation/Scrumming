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

import org.hibernate.validator.constraints.NotBlank;

import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import br.com.scrumming.domain.enuns.SituacaoItemBacklogEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ItemBacklog")
public class ItemBacklog extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_backlog")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "FK_projeto", referencedColumnName = "PK_projeto")
    private Projeto projeto;

    @Column(name = "nome", columnDefinition = "varchar(50)")
    @NotBlank
    private String nome;

    @Column(name = "descricao", columnDefinition = "varchar(500)")
    @NotBlank
    private String descricao;

    @Column(name = "criterio_aceitacao", columnDefinition = "varchar(300)")
    @NotBlank
    private String criterioAceitacao;

    @Column(name = "valor_negocio", columnDefinition = "double")
    @NotNull
    private Double valorNegocio;

    @Column(name = "story_points", columnDefinition = "int")
    @NotNull
    private Integer storyPoints;

    @Column(name = "roi", columnDefinition = "double")
    @NotNull
    private Double roi;

    @Column(name = "situacao_backlog", columnDefinition = "int")
    private SituacaoItemBacklogEnum situacaoBacklog;
    
    @Column(name = "is_ativo", columnDefinition = "bit")
    private boolean isAtivo;
    

	/* getters and setters */
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

    public SituacaoItemBacklogEnum getSituacaoBacklog() {
        return situacaoBacklog;
    }

    public void setSituacaoBacklog(SituacaoItemBacklogEnum situacaoBacklog) {
        this.situacaoBacklog = situacaoBacklog;
    }
    
    public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
}