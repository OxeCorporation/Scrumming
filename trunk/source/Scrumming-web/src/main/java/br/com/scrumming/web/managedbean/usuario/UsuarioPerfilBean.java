package br.com.scrumming.web.managedbean.usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.EmpresaClientService;
import br.com.scrumming.web.clientService.UsuarioClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.PaginasUtil;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class UsuarioPerfilBean extends AbstractBean {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{sessaoMB.usuario}")
	private Usuario usuarioLogado;
	@ManagedProperty(value="#{sessaoMB.empresaSelecionada}")
	private Empresa empresa;	
	private UsuarioClientService usuarioClientService;
	private EmpresaClientService empresaClientService;
	
	@Override
	protected void inicializar() {
		usuarioClientService = new UsuarioClientService();
		empresaClientService = new EmpresaClientService();
	}
	
	public String atualizar(){
		usuarioClientService.atualizarUsuario(usuarioLogado);
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return redirecionar(PaginasUtil.Geral.BENVINDO_PAGE);
	}
	
	public String atualizarNomeEmpresa() {
		empresaClientService.atualizarEmpresa(empresa);
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
		return "";
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		usuarioLogado.setFoto(event.getFile().getContents());
	}
	
	/*getters and setters*/
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}