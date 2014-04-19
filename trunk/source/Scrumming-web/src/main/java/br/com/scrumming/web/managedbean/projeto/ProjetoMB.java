package br.com.scrumming.web.managedbean.projeto;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.ProjetoDTO;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.ProjetoClientService;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class ProjetoMB extends AbstractBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private List<Projeto> projetosDaEmpresa;
	@FlashScoped
    private Projeto projeto;
    @FlashScoped
    private Projeto projetoSelecionado;
    @FlashScoped
    private ProjetoDTO projetoDTO;
    private ProjetoClientService clienteService;
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;
    @FlashScoped
	private List<Usuario> usuarioEmpresa;
    @FlashScoped
	private List<Usuario> usuarioEmpresaNotProjeto;
    @FlashScoped
	private List<Team> teamProjeto;

    
    @Override
    public void inicializar() {
        clienteService = new ProjetoClientService();
        projeto = new Projeto();
        projetosDaEmpresa = clienteService.consultarProjetosPorEmpresa(empresa.getCodigo());
    }
    
	public String consultarProjetoDTO() {
		projetoDTO = clienteService.consultarProjtoDTO(projetoSelecionado.getCodigo());
		usuarioEmpresaNotProjeto = projetoDTO.getUsuarioEmpresaNotTeam();
		teamProjeto = projetoDTO.getTimeProjeto();
		return projetoCadastroPage();
	}

    
	public String consultarUsuarioEmpresa() {
		usuarioEmpresa = clienteService.consultarUsuarioPorEmpresa(empresa.getCodigo());
		return projetoCadastroPage();
	}

	public String consultarUsuarioForaDoProjeto() {
		usuarioEmpresa = clienteService.consultarUsuarioPorEmpresaForaDoProjeto(projetoSelecionado.getCodigo(), empresa.getCodigo());
		return projetoCadastroPage();
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

    public ProjetoDTO getProjetoDTO() {
		return projetoDTO;
	}

	public void setProjetoDTO(ProjetoDTO projetoDTO) {
		this.projetoDTO = projetoDTO;
	}

	public List<Usuario> getUsuarioEmpresaNotProjeto() {
		return usuarioEmpresaNotProjeto;
	}

	public void setUsuarioEmpresaNotProjeto(List<Usuario> usuarioEmpresaNotProjeto) {
		this.usuarioEmpresaNotProjeto = usuarioEmpresaNotProjeto;
	}

	public List<Team> getTeamProjeto() {
		return teamProjeto;
	}

	public void setTeamProjeto(List<Team> teamProjeto) {
		this.teamProjeto = teamProjeto;
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

	public List<Usuario> getUsuarioEmpresa() {
		return usuarioEmpresa;
	}

	public void setUsuarioEmpresa(List<Usuario> usuarioEmpresa) {
		this.usuarioEmpresa = usuarioEmpresa;
	}
}