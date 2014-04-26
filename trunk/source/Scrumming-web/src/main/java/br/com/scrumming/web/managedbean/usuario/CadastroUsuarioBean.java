package br.com.scrumming.web.managedbean.usuario;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.clientService.UsuarioEmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean extends AbstractBean {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private UsuarioEmpresaClientService empresaService;
    private UsuarioClientService usuarioClientService;
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;

    public String pesquisarPorNome(){
    	return "";
    }
    	
    @Override
    public void inicializar() {
    	usuarioClientService = new UsuarioClientService();
    	empresaService = new UsuarioEmpresaClientService();
    	atualizarLista();
    }

    private void atualizarLista(){
    	 usuarios = empresaService.consultarUsuariosPorEmpresa(empresa.getCodigo());
    }
    
    public String sprintPage(){
    	return redirecionar(PaginasUtil.Sprint.SPRINT_PAGE);
    }

    
    public void salvar() {
    	usuarioClientService.salvarUsuario(usuario, empresa.getCodigo());
    	atualizarLista();
    	usuario = new Usuario();
    	mensagemSucesso();
    }

	private void mensagemSucesso() {
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
    
    public String novoUsuario(){
    	usuario = new Usuario();
    	return "";
    }
    public void alterar() {
    }

    public String desativar() {
    	usuarioClientService.desativarUsuario(usuario.getCodigo(),empresa.getCodigo());
    	atualizarLista();
    	mensagemSucesso();
    	return StringUtils.EMPTY;
    }

    public String ativar(){
    	usuarioClientService.ativarUsuario(usuario.getCodigo(),empresa.getCodigo());
    	atualizarLista();
    	mensagemSucesso();
    	return StringUtils.EMPTY;
    }
    /* getters and setters */

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}