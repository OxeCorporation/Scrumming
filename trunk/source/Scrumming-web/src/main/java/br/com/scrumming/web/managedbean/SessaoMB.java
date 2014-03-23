package br.com.scrumming.web.managedbean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.collections.CollectionUtils;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.clientService.UsuarioEmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;

@ManagedBean
@SessionScoped
public class SessaoMB {

	private UsuarioClientService usuarioClientService = new UsuarioClientService();
	private UsuarioEmpresaClientService usuarioEmpresaClientService = new UsuarioEmpresaClientService();
	private Usuario usuario;
	private List<Empresa> empresas;
	private Empresa empresaSelecionada;
	
	private String senha;
	private String login;

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
		if(CollectionUtils.isEmpty(empresas)){
			FacesMessageUtil
			.adicionarMensagemErro(ConstantesMensagem.MENSAGEM_ERRO_EMPRESA_VINCULADA);
			return;
		}else if(empresas.size() == 1){
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

	public String bemvindoPage() {
		return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
	}

	public String sprintPage() {
		return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
	}

	public String itemBacklogPage() {
		return redirecionar(PaginasUtil.ItemBacklog.ITEM_BACKLOG_PAGE);
	}

	public String cadastroUsuarioPage() {
		return redirecionar(PaginasUtil.Usuario.CADASTRO_USUARIO_PAGE);
	}

	public String projetoPage(){
		return redirecionar(PaginasUtil.Projeto.PROJETO_PAGE);
	}
	
	private String redirecionar(String page){
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
}