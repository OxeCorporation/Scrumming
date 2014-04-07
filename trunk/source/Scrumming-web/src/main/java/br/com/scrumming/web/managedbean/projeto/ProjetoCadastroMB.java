package br.com.scrumming.web.managedbean.projeto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ProjetoCadastroMB extends AbstractBean {

	private static final long serialVersionUID = 1L;
	@FlashScoped
    private Projeto projetoSelecionado;
	private ProjetoDTO projetoDTO;
	private ProjetoClientService projetoClientService;
	
	@Override
	protected void inicializar() {
		@SuppressWarnings("unused")
		int o = 1;
		projetoDTO = new ProjetoDTO();
		projetoClientService = new ProjetoClientService();
	}
	
	public void salvarProjeto(){
		projetoClientService.salvarProjeto(projetoDTO);
	}
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}	

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}
}