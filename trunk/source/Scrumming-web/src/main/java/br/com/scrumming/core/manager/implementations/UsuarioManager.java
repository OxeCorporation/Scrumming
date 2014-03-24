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
import br.com.scrumming.core.repositorio.UsuarioRepositorio;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

@Service
public class UsuarioManager extends AbstractManager<Usuario, Integer> implements IUsuarioManager {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private IEmpresaManager empresaManager;
    
    @Autowired
    private IUsuarioEmpresaManager usuarioEmpresaManager;
    
    @Override
    public AbstractRepositorio<Usuario, Integer> getRepositorio() {
        return this.usuarioRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> consultarPorNome(String nome) {
        return usuarioRepositorio.consultarPorNome(nome);
    }


	@Override
	public void salvarUsuario(Usuario usuario, Integer empresaID) {
		
		Empresa empresa = empresaManager.findByKey(empresaID);
		
		usuario.setAtivo(true);
		
		insertOrUpdate(usuario);
		
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setAtivo(true);
		
		usuarioEmpresaManager.insertOrUpdate(usuarioEmpresa);
	}
    
    /* getters and sertters */
    public UsuarioRepositorio getUsuarioRepositorio() {
        return usuarioRepositorio;
    }

    public void setUsuarioRepositorio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario consultarPorLoginSenha(String login, String senha) {
        return usuarioRepositorio.consultarPorLoginSenha(login, senha);
    }

	public IEmpresaManager getEmpresaManager() {
		return empresaManager;
	}

	public void setEmpresaManager(IEmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public IUsuarioEmpresaManager getUsuarioEmpresaManager() {
		return usuarioEmpresaManager;
	}

	public void setUsuarioEmpresaManager(
			IUsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}