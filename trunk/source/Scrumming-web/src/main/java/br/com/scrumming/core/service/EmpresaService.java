package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IEmpresaManager;
import br.com.scrumming.domain.Empresa;

@RestController
@RequestMapping("/empresa")
public class EmpresaService {

	@Autowired
	private IEmpresaManager empresaManager;
	
	/**
	 * Consultar Empresas pelo nome
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{nome}")
    public List<Empresa> consultarPorNome(@PathVariable String nome) {

        List<Empresa> listaEmpresas;

        if (StringUtils.isNotBlank(nome)) {
            listaEmpresas = new ArrayList<Empresa>(empresaManager.consultarPorNome(nome));
        } else {
            listaEmpresas = new ArrayList<Empresa>(empresaManager.findAll());
        }

        return listaEmpresas;
    }
	
	/**
	 * Consultar Empresas pelo código
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/list/{empresaID}")
    public List<Empresa> consultarPorCodigo(@PathVariable Integer empresaID) {
    	return new ArrayList<Empresa>(empresaManager.consultarPorCodigo(empresaID));
    }
	
	/**
	 * Listar todas as Empresas
	 * @return Uma lista de Empresas
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Empresa> listarTodas() {
		return new ArrayList<Empresa>(empresaManager.findAll());
	}
	
	/**
	 * Salvar uma empresa
	 * @param Empresa
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{empresa}")
    public void salvar(@PathVariable @Valid Empresa empresa) {
        this.empresaManager.insertOrUpdate(empresa);
    }
	
	/**
	 * Salvar uma empresa
	 * @param Empresa
	 * @return String informando Empresa Salva
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/empresa")
    public String salvarEmpresa(@RequestBody Empresa empresa){
    	return empresa.getNome() + " Salva";
    }
	
	/* getters and setters */
	public IEmpresaManager getEmpresaManager() {
		return empresaManager;
	}

	public void setEmpresaManager(IEmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}