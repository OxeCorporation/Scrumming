package br.com.scrumming.web.managedbean.empresa;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.domain.EmpresaDTO;
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
	private EmpresaDTO empresaDTO;
	private EmpresaClientService empresaClientService;
	
	/**
	 * Inicializar objetos da Empresa
	 * @param
	 * @return void
	 */
	@Override
    public void inicializar() {
		empresaDTO = new EmpresaDTO();
		empresaClientService = new EmpresaClientService();
	}
	
	/**
	 * Salvar uma empresa
	 * @param
	 * @return void
	 */
	public void salvar() {
		empresaClientService.salvar(empresaDTO);
		empresaDTO = new EmpresaDTO();
		mensagemSucesso();
	}
	
	private void mensagemSucesso() {
		FacesMessageUtil.adicionarMensagemInfo(ConstantesMensagem.MENSAGEM_OPERACAO_SUCESSO);
	}
	
	/* getters and setters */
	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}

	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}
}