package br.com.scrumming.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.scrumming.core.infra.repositorio.HibernateTypes;
import br.com.scrumming.core.infra.repositorio.ObjetoPersistente;

@Entity
@Table(name = "Empresa")
public class Empresa extends ObjetoPersistente<Integer> {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PK_empresa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@Column(name="nome", columnDefinition = "varchar(50)")
	@NotBlank
	private String nome;
	
	@Column(name="login", columnDefinition = "varchar(30)")
	@NotBlank
	private String login;
	
	@Column(name="senha", columnDefinition = "varchar(32)")
	@NotBlank
	private String senha;
	
	@Column(name="email", columnDefinition = "varchar(50)")
	@NotBlank
	private String email;
	
	@Type(type = HibernateTypes.JODA_DATE_TIME)
	@Column(name="data_cadastro")
	@NotNull
	private DateTime dataCadastro;
	
	@Column(name="is_ativo", columnDefinition= "bit")
	private boolean isAtivo;
	
	/*getters and setters*/
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(DateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isAtivo() {
		return isAtivo;
	}

	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
}