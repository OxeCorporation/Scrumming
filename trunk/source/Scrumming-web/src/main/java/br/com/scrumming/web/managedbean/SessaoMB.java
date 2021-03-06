package br.com.scrumming.web.managedbean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.Team;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.TeamClientService;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.clientService.UsuarioEmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean(name = "sessaoMB")
@SessionScoped
public class SessaoMB {

	private TeamClientService teamClienteService = new TeamClientService();
	private UsuarioClientService usuarioClientService = new UsuarioClientService();
	private UsuarioEmpresaClientService usuarioEmpresaClientService = new UsuarioEmpresaClientService();
	private Usuario usuario;
	private List<Empresa> empresas;
	private Empresa empresaSelecionada;
	private String senha;
	private String login;
	private Projeto projetoSelecionado;
	private Team time;

	public String efetuarLogin() {
		usuario = usuarioClientService.obterUsuario(login, senha);
		if (usuario == null) {
			FacesMessageUtil
					.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_LOGIN_SENHA_INVALIDO);
			return "";
		}
		configurarEmpresa();
		return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
	}

	private void configurarEmpresa() {
		empresas = usuarioEmpresaClientService
				.consultarEmpresasPorUsuario(usuario.getCodigo());
		if (CollectionUtils.isEmpty(empresas)) {
			FacesMessageUtil
					.adicionarMensagemErro(ConstantesMensagem.MENSAGEM_ERRO_EMPRESA_VINCULADA);
			return;
		} else if (empresas.size() == 1) {
			empresaSelecionada = empresas.get(0);
		}
	}

	public boolean isUsuarioLogado() {
		return usuario != null;
	}

	public String logout() {
		usuario = null;
		senha = null;
		login = null;
		return redirecionar(PaginasUtil.Geral.LOGIN_PAGE);
	}

	public String configurarTeam() {
		time = new Team();
		time.setEmpresa(empresaSelecionada);
		time.setProjeto(projetoSelecionado);
		time.setUsuario(usuario);
		time = teamClienteService.consultarTimeProjeto(time);
		return "";
	}

	public StreamedContent getFotoUsuario() {
		StreamedContent content = null;
		if (usuario.getFoto() != null) {
			content = new DefaultStreamedContent(new ByteArrayInputStream(
					usuario.getFoto()), "image/png");
		} else {
			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			InputStream input = externalContext
					.getResourceAsStream("/resources/images/default_user.png");
			content = new DefaultStreamedContent(input);
		}
		return content;
	}

	public String bemvindoPage() {
		return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
	}

	public String cadastroEmpresa() {
		return redirecionar(PaginasUtil.Empresa.CADASTRO_EMPRESA);
	}

	public String cadastroUsuarioPage() {
		return redirecionar(PaginasUtil.Usuario.CADASTRO_USUARIO_PAGE);
	}

	public String projetoPage() {
		return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
	}

	public String perfilPage() {
		return redirecionar(PaginasUtil.Usuario.PERFIL_USUARIO_PAGE);
	}

	private String redirecionar(String page) {
		return page + "?faces-redirect=true";
	}

	/* getters and setters */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public Team getTime() {
		return time;
	}

	public void setTime(Team time) {
		this.time = time;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
		configurarTeam();
	}
}