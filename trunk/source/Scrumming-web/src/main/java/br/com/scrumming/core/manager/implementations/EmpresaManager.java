package br.com.scrumming.core.manager.implementations;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.scrumming.core.infra.exceptions.NegocioException;
import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.infra.util.ConstantesMensagem;
import br.com.scrumming.core.manager.interfaces.IEmpresaManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
import br.com.scrumming.core.manager.interfaces.IUsuarioManager;
import br.com.scrumming.core.repositorio.EmpresaRepositorio;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.EmpresaDTO;
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
    public void salvar(EmpresaDTO empresaDTO) {
    	
		if (emailDuplicado(empresaDTO.getUsuario().getEmail())) {
			throw new NegocioException(ConstantesMensagem.ERRO_EMAIL_JA_EXISTE);
		} else if (loginDuplicado(empresaDTO.getUsuario().getLogin())) {
			throw new NegocioException(ConstantesMensagem.ERRO_LOGIN_JA_EXISTE);
		} else {
			//Salva a empresa
			Empresa empresa = empresaDTO.getEmpresa();
			empresa.setAtivo(true);
			empresa.setDataCadastro(DateTime.now());
			insertOrUpdate(empresa);
			
			//Salva o usuário
			Usuario usuario = empresaDTO.getUsuario();
			usuario.setAtivo(true);
			usuario.setEmpresa(true);
			usuarioManager.insertOrUpdate(usuario);
			
			//Salva o usuário empresa
			UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
			usuarioEmpresa.setEmpresa(empresa);
			usuarioEmpresa.setUsuario(usuario);
			usuarioEmpresa.setAtivo(true);
			usuarioEmpresaManager.insertOrUpdate(usuarioEmpresa);
		}		
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
	
	private boolean emailDuplicado(String email) {
		boolean duplicado = false;
		List<Usuario> usuarioConsulta = new ArrayList<Usuario>(usuarioManager.consultarPorCampo("email", email));
		if (usuarioConsulta.size() > 0) {
			duplicado = true;
		}
		return duplicado;
	}

	
	private boolean loginDuplicado(String login) {
		boolean duplicado = false;
		List<Usuario> usuarioConsulta = new ArrayList<Usuario>(usuarioManager.consultarPorCampo("login", login));
		if (usuarioConsulta.size() > 0) {
			duplicado = true;
		}
		return duplicado;
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