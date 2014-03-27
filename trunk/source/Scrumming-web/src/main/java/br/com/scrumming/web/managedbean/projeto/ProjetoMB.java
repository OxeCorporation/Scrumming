package br.com.scrumming.web.managedbean.projeto;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.managedbean.AbstractBean;

@ManagedBean
@ViewScoped
public class ProjetoMB extends AbstractBean {

    private List<Projeto> projetosDaEmpresa;
    private Projeto projeto;
    @FlashScoped
    private Projeto projetoSelecionado;
    private ProjetoClientService clienteService;
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;

    @Override
    public void inicializar() {
        clienteService = new ProjetoClientService();
        projetosDaEmpresa = clienteService.consultarProjetosPorEmpresa(empresa.getCodigo());
    }
    
    /*Métodos de redirecionamento das páginas*/
    public String projetoDetailPage() {
    	return redirecionar(PaginasUtil.Projeto.PROJETO_DETAIL_PAGE);
    }
	
	public String projetoCadastroPage() {
		return redirecionar(PaginasUtil.Projeto.PROJETO_CADASTRO_PAGE);
	}

    /* getters and setters */
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

	public List<Projeto> getProjetosDaEmpresa() {
		return projetosDaEmpresa;
	}

	public void setProjetosDaEmpresa(List<Projeto> projetosDaEmpresa) {
		this.projetosDaEmpresa = projetosDaEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}