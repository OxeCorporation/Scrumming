package br.com.scrumming.interfaces;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnItemBacklog {
	void itemBacklogFoiClicada(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint);
}