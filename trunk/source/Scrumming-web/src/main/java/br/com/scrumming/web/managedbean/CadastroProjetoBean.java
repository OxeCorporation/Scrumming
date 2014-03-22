package br.com.scrumming.web.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ProjetoClientService;

@ManagedBean
@ViewScoped
public class CadastroProjetoBean extends AbstractBean {

    
	private Empresa empresa;
	private List<Projeto> projetos;
    private Projeto projetoSelecionado;
    private ProjetoClientService clienteService;
    
    @Override
    public void inicializar() {
        projetos = new ArrayList<Projeto>();
        clienteService = new ProjetoClientService();
    }

    public String novo() {
        return "";
    }

    public String detalhar() {
        return "";

    }

    public String alterar() {
        return "";

    }

    public String excluir() {
        return "";
    }

    /* getters and setters */
    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    
    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

}