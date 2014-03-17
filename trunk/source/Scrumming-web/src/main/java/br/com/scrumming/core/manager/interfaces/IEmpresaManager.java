package br.com.scrumming.core.manager.interfaces;

import java.util.List;

import br.com.scrumming.core.infra.manager.IManager;
import br.com.scrumming.domain.Empresa;

public interface IEmpresaManager extends IManager<Empresa, Integer> {

	List<Empresa> consultarPorNome(String nome);
	
	List<Empresa> consultarPorCodigo(Integer empresaID);
}