package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.List;

public class ProjetoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private Projeto projeto;
	
	private List<Team> timeProjeto;
	
	private List<Usuario> usuarioEmpresa;

	// Teste commit Ubuntu
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

	public List<Team> getTimeProjeto() {
		return timeProjeto;
	}

	public void setTimeProjeto(List<Team> timeProjeto) {
		this.timeProjeto = timeProjeto;
	}

	public List<Usuario> getUsuarioEmpresa() {
		return usuarioEmpresa;
	}

	public void setUsuarioEmpresa(List<Usuario> usuarioEmpresa) {
		this.usuarioEmpresa = usuarioEmpresa;
	}
	
	
}
