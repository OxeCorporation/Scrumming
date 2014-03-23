package br.com.scrumming.core.manager.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scrumming.core.infra.manager.AbstractManager;
import br.com.scrumming.core.infra.repositorio.AbstractRepositorio;
import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
import br.com.scrumming.core.repositorio.UsuarioEmpresaRepositorio;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

@Service
public class UsuarioEmpresaManager extends
		AbstractManager<UsuarioEmpresa, Integer> implements
		IUsuarioEmpresaManager {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioEmpresaRepositorio usuarioEmpresaRepositorio;

	@Override
	public List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID) {
		return usuarioEmpresaRepositorio.consultarUsuarioPorEmpresa(empresaID);
	}
	
	@Override
	public List<Empresa> consultarEmpresaPorUsuario(Integer usuarioID) {
		return usuarioEmpresaRepositorio.consultarEmpresaPorUsuario(usuarioID);
	}

	/* getters and setters */
	@Override
	public AbstractRepositorio<UsuarioEmpresa, Integer> getRepositorio() {
		return this.usuarioEmpresaRepositorio;
	}

	public UsuarioEmpresaRepositorio getUsuarioEmpresaRepositorio() {
		return usuarioEmpresaRepositorio;
	}

	public void setUsuarioEmpresaRepositorio(
			UsuarioEmpresaRepositorio usuarioEmpresaRepositorio) {
		this.usuarioEmpresaRepositorio = usuarioEmpresaRepositorio;
	}

}