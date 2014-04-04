package br.com.scrumming.web.managedbean.usuario;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.clientService.UsuarioEmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;
import br.com.scrumming.web.infra.jsf.ListaDataModel;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean extends AbstractBean {

	private Usuario usuarioSelecionado;
	private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private ListaDataModel<Usuario> dataModelUsuario;
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
         dataModelUsuario = new ListaDataModel<Usuario>(usuarios);
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
    public String alterar() {
    	if(usuarioSelecionado == null){
    		//TODO esdras - MEnsagem de erro nenhun usuario selecionado
    	}else{
    		usuario = usuarioSelecionado;
    		
    	}
        return "";
    }

    public String desativar() {
    	usuarioClientService.desativarUsuario(usuarioSelecionado.getCodigo(),empresa.getCodigo());
    	atualizarLista();
    	mensagemSucesso();
        return "";
    }

    /* getters and setters */

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
    		this.usuarioSelecionado = usuarioSelecionado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

	public ListaDataModel<Usuario> getDataModelUsuario() {
		return dataModelUsuario;
	}

	public void setDataModelUsuario(ListaDataModel<Usuario> dataModelUsuario) {
		this.dataModelUsuario = dataModelUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}