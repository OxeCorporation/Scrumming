package br.com.scrumming.interfaces;

import br.com.scrumming.domain.Empresa;
import br.com.scrumming.domain.Usuario;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnEmpresa {
	void empresaFoiClicada(UsuarioEmpresa usuarioEmpresa);
}
