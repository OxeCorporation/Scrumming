package br.com.scrumming.web.managedbean.projeto;

import java.util.ArrayList;
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

    private static final long serialVersionUID = 1L;
	@FlashScoped
	private List<Projeto> projetosAtivosDaEmpresa;
	@FlashScoped
	private List<Projeto> projetosConcluidosDaEmpresa;
	@FlashScoped
	private List<Projeto> projetoTodosDaEmpresa;
	@FlashScoped
    private Projeto projeto;
    @FlashScoped
    private Projeto projetoSelecionado;
    @FlashScoped
    private ProjetoDTO projetoDTO;
    private ProjetoClientService projetoClienteService;
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;
    @FlashScoped
	private List<Usuario> usuarioEmpresaNotProjeto;
    @FlashScoped
	private List<Team> teamProjeto;
    @FlashScoped
    private String titulo;
        
    @Override
    public void inicializar() {
        projetoClienteService = new ProjetoClientService();
        projeto = new Projeto();
        projetoTodosDaEmpresa = projetoClienteService.consultarProjetosPorEmpresa(empresa.getCodigo());
        projetosAtivosDaEmpresa = projetoClienteService.consultarProjetosAtivosPorEmpresa(empresa.getCodigo());
        projetosConcluidosDaEmpresa = projetoClienteService.consultarProjetosConcluidosPorEmpresa(empresa.getCodigo());
    }
    
    public String consultarProjetoDTO() {
		projetoDTO = projetoClienteService.consultarProjtoDTO(projetoSelecionado.getCodigo());
		projetoSelecionado = projetoDTO.getProjeto();
		usuarioEmpresaNotProjeto = projetoDTO.getUsuarioEmpresaNotTeam();
		teamProjeto = projetoDTO.getTimeProjeto();
		setTitulo("Alteração de Projeto");
		return projetoCadastroPage();
	}

	public String consultarUsuarioEmpresa() {
		projetoDTO = new ProjetoDTO();
		teamProjeto = new ArrayList<>();
		usuarioEmpresaNotProjeto = projetoClienteService.consultarUsuarioPorEmpresa(empresa.getCodigo());
		setTitulo("Cadastro de Projeto");
		return projetoCadastroPage();
	}

	public String consultarUsuarioForaDoProjeto() {
		usuarioEmpresaNotProjeto = projetoClienteService.consultarUsuarioPorEmpresaForaDoProjeto(projetoSelecionado.getCodigo(), empresa.getCodigo());
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
		return projetoClienteService;
	}

	public void setClienteService(ProjetoClientService clienteService) {
		this.projetoClienteService = clienteService;
	}

	public List<Projeto> getProjetosAtivosDaEmpresa() {
		return projetosAtivosDaEmpresa;
	}

	public void setProjetosAtivosDaEmpresa(List<Projeto> projetosAtivosDaEmpresa) {
		this.projetosAtivosDaEmpresa = projetosAtivosDaEmpresa;
	}

	public List<Projeto> getProjetosConcluidosDaEmpresa() {
		return projetosConcluidosDaEmpresa;
	}

	public void setProjetosConcluidosDaEmpresa(
			List<Projeto> projetosConcluidosDaEmpresa) {
		this.projetosConcluidosDaEmpresa = projetosConcluidosDaEmpresa;
	}

	public List<Projeto> getProjetoTodosDaEmpresa() {
		return projetoTodosDaEmpresa;
	}

	public void setProjetoTodosDaEmpresa(List<Projeto> projetoTodosDaEmpresa) {
		this.projetoTodosDaEmpresa = projetoTodosDaEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}