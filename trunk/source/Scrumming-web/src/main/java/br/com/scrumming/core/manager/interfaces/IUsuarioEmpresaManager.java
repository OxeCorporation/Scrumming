package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface IUsuarioEmpresaManager extends IManager<UsuarioEmpresa, Integer> {

	/**
	 * Consulta os usuario de uma determinada empresa
	 * @param empresaID
	 * @return List<Usuario>
	 */
	List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID);

	/**
	 * Consulta Todas as empresas daquele usuario em que ele esteja ativo 
	 * 
	 * @param usuarioID
	 * @return	List<Empresa> 
	 */
	List<Empresa> consultarEmpresaPorUsuario(Integer usuarioID);

}