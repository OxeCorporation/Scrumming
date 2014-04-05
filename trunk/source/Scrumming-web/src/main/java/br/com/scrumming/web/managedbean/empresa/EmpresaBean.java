package br.com.scrumming.web.managedbean.empresa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.web.clientService.EmpresaClientService;
import br.com.scrumming.web.infra.FacesMessageUtil;
import br.com.scrumming.web.infra.FlashScoped;
import br.com.scrumming.web.infra.bean.AbstractBean;

@ManagedBean
@ViewScoped
public class EmpresaBean extends AbstractBean {

	@FlashScoped
	private Empresa empresa;
	private EmpresaClientService empresaClientService;
	
	/**
	 * Inicializar objetos da Empresa
	 * @param
	 * @return void
	 */
	@Override
    public void inicializar() {
		empresa = new Empresa();
		empresaClientService = new EmpresaClientService();
	}
	
	/**
	 * Salvar uma empresa
	 * @param
	 * @return void
	 */
	public void salvarEmpresa() {
		empresaClientService.salvarEmpresa(empresa);
    	FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	/* getters and setters */
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}