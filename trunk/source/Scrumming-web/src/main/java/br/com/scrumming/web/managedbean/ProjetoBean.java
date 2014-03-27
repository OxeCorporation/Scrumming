package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@ViewScoped
@RequestScoped
@SessionScoped
public class ProjetoBean extends AbstractBean {

    private List<Projeto> projetosDaEmpresa;
    private Projeto projeto;
    @FlashScoped
    private Projeto projetoSelecionado;
    private ProjetoClientService clienteService;
    @ManagedProperty(value="#{sessaoMB}")
    private SessaoMB sessaoMB;

    @Override
    public void inicializar() {
        clienteService = new ProjetoClientService();
        consultarProjetosPorEmpresa(sessaoMB.getEmpresaSelecionada().getCodigo());
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
	
	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
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

	public SessaoMB getSessaoMB() {
		return sessaoMB;
	}

	public void setSessaoMB(SessaoMB sessaoMB) {
		this.sessaoMB = sessaoMB;
	}
}