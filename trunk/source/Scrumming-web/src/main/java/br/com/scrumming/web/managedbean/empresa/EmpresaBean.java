package br.com.scrumming.web.managedbean.empresa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.web.clientService.EmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class EmpresaBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FlashScoped
	private Empresa empresa;
	@FlashScoped
	private Usuario usuario;
	private EmpresaClientService empresaClientService;
	
	/**
	 * Inicializar objetos da Empresa
	 * @param
	 * @return void
	 */
	@Override
    public void inicializar() {
		empresa = new Empresa();
		usuario = new Usuario();
		empresaClientService = new EmpresaClientService();
	}
	
	/**
	 * Salvar uma empresa
	 * @param
	 * @return void
	 */
	public void salvar() {
		empresaClientService.salvar(empresa, usuario);
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	/* getters and setters */
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