package br.com.scrumming.web.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.service.EmpresaService;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@SessionScoped
public class SessaoMB extends AbstractBean {

    private UsuarioClientService usuarioClientService = new UsuarioClientService();
    private Usuario usuario;
    private Empresa empresa;
    private String senha;
    private String login;

    public String efetuarLogin() {
        usuario = usuarioClientService.obterUsuario(login, senha);
        if (usuario == null) {
            FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.LABEL_LOGIN_SENHA_INVALIDO);
            return "";
        }else if(usuario.isEmpresa()){
        	configurarEmpresa();
        }
        return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
    }

    private void configurarEmpresa() {
		
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

    public String bemvindoPage(){
    	return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
    }
    
    public String cadastroUsuarioPage(){
    	return redirecionar(PaginasUtil.Usuario.CADASTRO_USUARIO_PAGE);
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}