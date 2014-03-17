package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface IUsuarioEmpresaManager extends IManager<UsuarioEmpresa, Integer> {

	public List<Usuario> consultarUsuarioPorEmpresa(Integer empresaID);

}