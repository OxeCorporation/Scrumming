package br.com.scrumming.web.managedbean.sprint;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintCadastroMB extends AbstractBean {

	@FlashScoped
	private Sprint sprintSelecionada;
	@FlashScoped
	private Projeto projetoSelecionado;
	private int dias;
		
	/*Métodos para redirecionamento das páginas*/
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}
	
	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	/*Getters anda Setters*/
	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

	public void setSprintSelecionada(Sprint sprintSelecionada) {
		this.sprintSelecionada = sprintSelecionada;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}
}
