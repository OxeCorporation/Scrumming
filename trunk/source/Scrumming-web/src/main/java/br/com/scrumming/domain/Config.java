package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Config")
public class Config extends ObjetoPersistente<Integer> {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PK_config")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;
    
    @ManyToOne
    @JoinColumn(name="FK_empresa", referencedColumnName="PK_empresa")
    private Empresa empresa;

    @Column(name = "codigo_config")
    private Integer codigoConfig;
    
    @Column(name = "nome_config", columnDefinition = "varchar(50)")
    @NotBlank
    private String nomeConfig;
    
    @Column(name = "perfil_owner", columnDefinition = "bit")
    private boolean perfilOwner;
    
    @Column(name = "perfil_master", columnDefinition = "bit")
    private boolean perfilMaster;
    
    @Column(name = "perfil_team", columnDefinition = "bit")
    private boolean perfilTeam;
    
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getCodigoConfig() {
		return codigoConfig;
	}

	public void setCodigoConfig(Integer codigoConfig) {
		this.codigoConfig = codigoConfig;
	}

	public String getNomeConfig() {
		return nomeConfig;
	}

	public void setNomeConfig(String nomeConfig) {
		this.nomeConfig = nomeConfig;
	}

	public boolean isPerfilOwner() {
		return perfilOwner;
	}

	public void setPerfilOwner(boolean perfilOwner) {
		this.perfilOwner = perfilOwner;
	}

	public boolean isPerfilMaster() {
		return perfilMaster;
	}

	public void setPerfilMaster(boolean perfilMaster) {
		this.perfilMaster = perfilMaster;
	}

	public boolean isPerfilTeam() {
		return perfilTeam;
	}

	public void setPerfilTeam(boolean perfilTeam) {
		this.perfilTeam = perfilTeam;
	}
}