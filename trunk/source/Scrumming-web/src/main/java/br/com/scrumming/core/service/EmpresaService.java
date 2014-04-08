package br.com.scrumming.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IEmpresaManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;

@RestController
@RequestMapping("/empresa")
public class EmpresaService {

	@Autowired
	private IEmpresaManager empresaManager;
	
	/**
	 * Salvar uma empresa
	 * @param Empresa
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/salvar/")
    public void salvar(@RequestBody Empresa empresa, @RequestBody Usuario usuario) {
		empresaManager.salvar(empresa, usuario);
    }
	
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
	
	/* getters and setters */
	public IEmpresaManager getEmpresaManager() {
		return empresaManager;
	}

	public void setEmpresaManager(IEmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}