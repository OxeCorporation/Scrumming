package br.com.scrumming.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.scrumming.core.manager.interfaces.IUsuarioEmpresaManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

@RestController
@RequestMapping("/usuario_empresa")
public class UsuarioEmpresaService {

	@Autowired
	private IUsuarioEmpresaManager usuarioEmpresaManager;

	@RequestMapping(value = "/empresadousuario/{usuarioID}", method= RequestMethod.GET)
	public List<UsuarioEmpresa> consultarEmpresasDoUsuario(@PathVariable Integer usuarioID){
		return this.usuarioEmpresaManager.consultarEmpresasDoUsuario(usuarioID);
	}
	
	@RequestMapping(value = "/usuario/{usuarioID}", method= RequestMethod.GET)
	public List<Empresa> consultarEmpresasPorUsuario(@PathVariable Integer usuarioID){
		return this.usuarioEmpresaManager.consultarEmpresaPorUsuario(usuarioID);
	}
	@RequestMapping(value="/empresa/{empresaID}", method= RequestMethod.GET)
	public List<Usuario> consultarUsuariosPorEmpresa(@PathVariable Integer empresaID){
		return this.usuarioEmpresaManager.consultarUsuarioPorEmpresa(empresaID);
	}
	
	
	//List<UsuarioEmpresa> consultarEmpresasDoUsuario(Integer usuarioID)
	
	/*gettters and setters*/
	public IUsuarioEmpresaManager getUsuarioEmpresaManager() {
		return usuarioEmpresaManager;
	}

	public void setUsuarioEmpresaManager(
			IUsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}