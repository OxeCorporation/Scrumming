package br.com.scrumming.web.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.scrumming.core.service.UsuarioService;
import br.com.scrumming.domain.Usuario;

@ManagedBean
@SessionScoped
public class SessaoMB {

    private UsuarioService usuarioService = new UsuarioService();

    private Usuario usuario;

    private String senha;
    private String login;

    public String efetuarLogin() {
        usuario = usuarioService.obterUsuario(login, senha);
        if (usuario != null) {
            return "/paginas/bemvindo.xhtml?faces-redirect=true";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Second Message", "login ou senha invalido"));
            return "";
        }
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