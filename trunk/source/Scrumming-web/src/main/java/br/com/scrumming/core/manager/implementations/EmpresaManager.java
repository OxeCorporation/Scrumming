package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IEmpresaManager;
import br.com.scrumming.core.repositorio.EmpresaRepositorio;
import br.com.scrumming.domain.Empresa;

@Service
public class EmpresaManager extends 
		AbstractManager<Empresa, Integer> implements 
		IEmpresaManager {

	 /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;
    
	@Autowired
    private EmpresaRepositorio empresaRepositorio;
	
	@Override
	public AbstractRepositorio<Empresa, Integer> getRepositorio() {
		return this.empresaRepositorio;
	}

	/**
	 * Consultar Empresas pelo nome
	 * @param Nome da Empresa
	 * @return Uma lista de Empresas
	 */
	@Override
    @Transactional(readOnly = true)
    public List<Empresa> consultarPorNome(String nome) {
        return empresaRepositorio.consultarPorNome(nome);
    }

	/**
	 * Consultar Empresas pelo código
	 * @param Código da Empresa
	 * @return Uma lista de Empresas
	 */
	@Override
	public List<Empresa> consultarPorCodigo(Integer EmpresaID) {
		return empresaRepositorio.consultarPorCodigo(EmpresaID);
	}

	/* getters and setters */
	public EmpresaRepositorio getEmpresaRepositorio() {
		return empresaRepositorio;
	}

	public void setEmpresaRepositorio(EmpresaRepositorio empresaRepositorio) {
		this.empresaRepositorio = empresaRepositorio;
	}
}