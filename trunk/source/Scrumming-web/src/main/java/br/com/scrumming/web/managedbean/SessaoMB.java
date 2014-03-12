package br.com.scrumming.web.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@SessionScoped
public class SessaoMB extends AbstractBean {

	private UsuarioClientService usuarioClientService = new UsuarioClientService();
	private Usuario usuario;

	private String senha;
	private String login;

	public String efetuarLogin() {
		usuario = usuarioClientService.obterUsuario(login, senha);
		if (usuario != null) {
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "INFO: ",
					"login ou senha invalido"));
			return "";
		}
		return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
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
}