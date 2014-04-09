package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IEmpresaManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.EmpresaRepositorio;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

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
	
	@Autowired
	private IUsuarioManager usuarioManager;
	
	@Autowired
	private IUsuarioEmpresaManager usuarioEmpresaManager;
	
	@Override
	public AbstractRepositorio<Empresa, Integer> getRepositorio() {
		return this.empresaRepositorio;
	}
	
	/**
	 * Salvar uma empresa
	 * @param Empresa
	 * @return void
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void salvar(Empresa empresa, Usuario usuario) {
    	//Salva a empresa
		empresa.setAtivo(true);
		insertOrUpdate(empresa);
		
		//Salva o usuário
		usuario.setAtivo(true);
		usuarioManager.insertOrUpdate(usuario);
		
		//Salva o usuário empresa
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setAtivo(true);
		usuarioEmpresaManager.insertOrUpdate(usuarioEmpresa);
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
	@Transactional(readOnly = true)
	public List<Empresa> consultarPorCodigo(Integer empresaID) {
		return empresaRepositorio.consultarPorCodigo(empresaID);
	}

	/* getters and setters */
	public EmpresaRepositorio getEmpresaRepositorio() {
		return empresaRepositorio;
	}

	public void setEmpresaRepositorio(EmpresaRepositorio empresaRepositorio) {
		this.empresaRepositorio = empresaRepositorio;
	}
}