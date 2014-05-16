package br.com.scrumming.interfaces;

import br.com.scrumming.domain.Projeto;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnProjeto {
	
	void projetoFoiClicado(Projeto projeto, UsuarioEmpresa usuarioEmpresa);

}
