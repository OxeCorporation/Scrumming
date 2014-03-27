package br.com.scrumming.web.managedbean.projeto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.managedbean.AbstractBean;

@ManagedBean
@ViewScoped
public class ProjetoDetalheMB extends AbstractBean {

	@FlashScoped
    private Projeto projetoSelecionado;	
		
	/*Métodos para o redirecionamento das páginas*/
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}	

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	/*Getters and Setters*/
	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
}