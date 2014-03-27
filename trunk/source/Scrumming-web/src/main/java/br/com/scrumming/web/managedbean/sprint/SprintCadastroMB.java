package br.com.scrumming.web.managedbean.sprint;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.managedbean.AbstractBean;

@ManagedBean
@ViewScoped
public class SprintCadastroMB extends AbstractBean {

	@FlashScoped
	private Sprint sprintSelecionada;
	
	@Override
	public void inicializar() {
		@SuppressWarnings("unused")
		int o = 1;
	}
	
	/*Métodos para redirecionamento das páginas*/
	public String sprintCadastroPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_CADASTRO_PAGE);
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
}
