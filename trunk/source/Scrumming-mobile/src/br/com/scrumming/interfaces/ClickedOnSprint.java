package br.com.scrumming.interfaces;

import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnSprint {
	void sprintFoiClicada(Sprint sprint, UsuarioEmpresa usuarioEmpresa);
}