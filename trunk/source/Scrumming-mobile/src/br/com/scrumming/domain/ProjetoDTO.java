package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjetoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	private Projeto projeto;
	private List<Team> timeProjeto;
	private List<Usuario> usuarioEmpresaNotTeam;
	
	public ProjetoDTO(){
		projeto = new Projeto();
		timeProjeto = new ArrayList<Team>();
		usuarioEmpresaNotTeam = new ArrayList<Usuario>();
	}

	/*getters and setters*/
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Usuario> getUsuarioEmpresaNotTeam() {
		return usuarioEmpresaNotTeam;
	}

	public void setUsuarioEmpresaNotTeam(List<Usuario> usuarioEmpresaNotTeam) {
		this.usuarioEmpresaNotTeam = usuarioEmpresaNotTeam;
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
		return usuarioEmpresaNotTeam;
	}

	public void setUsuarioEmpresa(List<Usuario> usuarioEmpresa) {
		this.usuarioEmpresaNotTeam = usuarioEmpresa;
	}
}