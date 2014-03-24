package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@ViewScoped
public class ProjetoBean extends AbstractBean {

    private List<Projeto> projetosDaEmpresa;
    private Projeto projeto;
    @FlashScoped
    private Projeto projetoSelecionado;
    private ProjetoClientService clienteService;

    @Override
    public void inicializar() {
        clienteService = new ProjetoClientService();
    }
    
    public ProjetoBean() {
    	inicializar();
    	consultarProjetosPorEmpresa(new Integer(1));
    }
    
    public String consultarProjetosPorEmpresa(Integer empresaID) {
    	projetosDaEmpresa = clienteService.consultarProjetosPorEmpresa(empresaID);
    	return "";
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
}