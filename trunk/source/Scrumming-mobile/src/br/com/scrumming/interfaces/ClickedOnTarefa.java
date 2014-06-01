package br.com.scrumming.interfaces;

import br.com.scrumming.domain.ItemBacklog;
import br.com.scrumming.domain.Sprint;
import br.com.scrumming.domain.UsuarioEmpresa;

public interface ClickedOnTarefa {

	void clicouNaTarefa(ItemBacklog itemBacklog, UsuarioEmpresa usuarioEmpresa, Sprint sprint);
}
