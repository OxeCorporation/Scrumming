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

	/**
	 * TESTE COM SVN
	 */
    private Usuario usuarioSelecionado;
    private List<Usuario> usuarios;
    private ListaDataModel<Usuario> dataModelUsuario;
    private UsuarioEmpresaClientService empresaService;
    private UsuarioClientService usuarioClientService;
    @ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
    private Empresa empresa;
    private boolean editavel = true;

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
    
    public String novo() {
    	usuarioClientService.salvarUsuario(usuarioSelecionado, empresa.getCodigo());
    	atualizarLista();
    	usuarioSelecionado = new Usuario();
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
        return "";
    }

    public String cadastrar(){
    	usuarioSelecionado = new Usuario();
    	return "";
    }
    
    public String alterar() {
    	if(usuarioSelecionado == null){
    	 // TODO esc exibir uma mensagem de usuario deve ser selecionado
    	}
        return "";
    }

    public String excluir() {
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

	public boolean isEditavel() {
		return editavel;
	}

	public void setEditavel(boolean editavel) {
		this.editavel = editavel;
	}
}
