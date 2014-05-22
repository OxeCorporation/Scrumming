package br.com.scrumming.interfaces;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnHome {

	void clicouNoHome(UsuarioEmpresa usuarioEmpresa);
	void clicouNoHome(UsuarioEmpresa usuarioEmpresa, Projeto projeto);
}
