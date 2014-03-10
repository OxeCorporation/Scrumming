package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.ProjetoClientService;

@ManagedBean
@ViewScoped
public class CadastroProjetoBean extends AbstractBean {

    private List<Projeto> projetos;
    private Projeto projeto;
    private Projeto projetoSelecionado;
    
    private ProjetoClientService clienteService;


    @Override
    public void inicializar() {
        projetos = new ArrayList<Projeto>();
        clienteService = new ProjetoClientService();
        projeto = new Projeto();
    }

    public String novo() {
    	clienteService.salvarProjeto(projeto);
        return "";
    }

    public String detalhar() {
        // TODO esc implementar depois
        return "";

    }

    public String alterar() {
       	clienteService.salvarProjeto(projeto);
        return "";

    }

    public String excluir() {
       	clienteService.deletarProjeto(projeto);
        return "";
    }

    /* getters and setters */
	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

}