package br.com.scrumming.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjetoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	
	private Date dataFim;
	
	private Date dataInicialcadastrada;
	
	private String dataInicialCadastradaFormatada;

	private Projeto projeto;
	
	private List<Team> timeProjeto;
	
	private List<Usuario> usuarioEmpresaNotTeam;
	
	public ProjetoDTO(){
		projeto = new Projeto();
		timeProjeto = new ArrayList<>();
		usuarioEmpresaNotTeam = new ArrayList<>();
	}

	// Teste commit Ubuntu
	
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

	public String getDataInicialCadastradaFormatada() {
		try {
			//dataInicialCadastradaFormatada =  getDataInicialcadastrada().toString("dd/MM/yyyy");
		} catch (NullPointerException e) {			
		}
		return dataInicialCadastradaFormatada;
	}

	public Date getDataInicialcadastrada() {
		return dataInicialcadastrada;
	}

	public void setDataInicialcadastrada(Date dataInicialcadastrada) {
		this.dataInicialcadastrada = dataInicialcadastrada;
	}

	public void setDataInicialCadastradaFormatada(
			String dataInicialCadastradaFormatada) {
		this.dataInicialCadastradaFormatada = dataInicialCadastradaFormatada;
	}

}
