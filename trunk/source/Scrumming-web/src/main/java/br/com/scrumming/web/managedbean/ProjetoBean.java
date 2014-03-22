package br.com.scrumming.web.managedbean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ProjetoClientService;

@ManagedBean
@ViewScoped
public class ProjetoBean extends AbstractBean {

    private List<Projeto> projetos;
    private Projeto projeto;
    private Projeto projetoSelecionado;
    private ProjetoClientService clienteService;

    @Override
    public void inicializar() {
        clienteService = new ProjetoClientService();
    }
    
    public ProjetoBean() {
    	inicializar();
    	consultarProjetosPorEmpresa();
    }
    
    public void consultarProjetosPorEmpresa() {
    	
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

    public ProjetoClientService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ProjetoClientService clienteService) {
		this.clienteService = clienteService;
	}
}